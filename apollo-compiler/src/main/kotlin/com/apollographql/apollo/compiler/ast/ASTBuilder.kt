package com.apollographql.apollo.compiler.ast

import com.apollographql.apollo.compiler.ir.*

fun CodeGenerationIR.toAST(
    customTypeMap: Map<String, String>,
    typesPackageName: String,
    fragmentsPackage: String,
    useSemanticNaming: Boolean
): AST {
  val enums = typesUsed.filter { it.kind == TypeDeclaration.KIND_ENUM }.map { it.toASTEnumType() }
  val inputTypes = typesUsed.filter { it.kind == TypeDeclaration.KIND_INPUT_OBJECT_TYPE }.map {
    it.toASTInputType(
        enums = enums,
        customTypeMap = customTypeMap,
        typesPackageName = typesPackageName
    )
  }
  val operations = operations.map { operation ->
    operation.toASTOperation(
        context = CodeGenContext(
            customTypeMap = customTypeMap,
            enums = enums,
            typesPackageName = typesPackageName,
            fragmentsPackage = fragmentsPackage,
            fragments = fragments.associate { it.fragmentName to it }
        ),
        useSemanticNaming = useSemanticNaming
    )
  }
  val fragments = fragments.map {
    it.toASTFragment(
        CodeGenContext(
            customTypeMap = customTypeMap,
            enums = enums,
            typesPackageName = typesPackageName,
            fragmentsPackage = fragmentsPackage,
            fragments = fragments.associate { it.fragmentName to it }
        )
    )
  }
  return AST(
      enums = enums,
      customTypes = customTypeMap,
      inputTypes = inputTypes,
      fragments = fragments,
      operations = operations
  )
}

private fun TypeDeclaration.toASTEnumType() = AST.EnumType(
    name = name,
    description = description ?: "",
    values = values?.map {
      AST.EnumType.Value(
          value = it.name,
          description = it.description ?: "",
          isDeprecated = it.isDeprecated ?: false,
          deprecationReason = it.deprecationReason ?: ""
      )
    } ?: emptyList()
)

private fun TypeDeclaration.toASTInputType(
    enums: List<AST.EnumType>,
    customTypeMap: Map<String, String>,
    typesPackageName: String
) = AST.InputType(
    name = name,
    description = description ?: "",
    fields = fields?.map {
      val inputFieldType = resolveFieldType(
          graphQLType = it.type,
          enums = enums,
          customTypeMap = customTypeMap,
          typesPackageName = typesPackageName,
          fallbackType = {
            AST.TypeRef(
                name = it.removeSuffix("!"),
                packageName = typesPackageName
            )
          }
      )
      AST.InputType.Field(
          name = it.name,
          type = inputFieldType,
          description = it.description,
          isOptional = !it.type.endsWith("!"),
          defaultValue = if (inputFieldType.isCustomType) null else it.defaultValue?.normalizeJsonValue(it.type)
      )
    } ?: emptyList()
)

private fun Operation.toASTOperation(
    context: CodeGenContext,
    useSemanticNaming: Boolean
): AST.OperationType {
  val dataTypeRef = context.addObjectType(typeName = "Data", packageName = normalizedOperationName(useSemanticNaming)) {
    AST.ObjectType(
        name = "Data",
        fields = fields.map { it.toASTField(context) },
        fragmentsType = null
    )
  }
  return AST.OperationType(
      name = normalizedOperationName(useSemanticNaming),
      type = astOperationType,
      definition = source,
      operationId = operationId,
      queryDocument = sourceWithFragments,
      variables = AST.InputType(
          name = "Variables",
          description = "",
          fields = variables.map { variable ->
            AST.InputType.Field(
                name = variable.name,
                type = resolveFieldType(
                    graphQLType = variable.type,
                    enums = context.enums,
                    customTypeMap = context.customTypeMap,
                    typesPackageName = context.typesPackageName,
                    fallbackType = {
                      AST.TypeRef(
                          name = it.removeSuffix("!"),
                          packageName = context.typesPackageName)
                    }
                ),
                isOptional = variable.optional(),
                defaultValue = null,
                description = ""
            )
          }
      ),
      data = dataTypeRef,
      nestedObjects = context.objectTypes,
      filePath = filePath
  )
}

private fun Field.toASTField(context: CodeGenContext): AST.ObjectType.Field {
  return when {
    isArrayTypeField -> toASTArrayField(context)
    isObjectTypeField -> toASTObjectField(context)
    else -> toASTScalarField(context)
  }
}

private fun Field.toASTArrayField(context: CodeGenContext): AST.ObjectType.Field {
  val fieldType = if (fields?.isNotEmpty() == true) {
    AST.FieldType.Array(
        AST.FieldType.Object(
            context.addObjectType(
                type = responseName,
                schemaType = type,
                fragmentSpreads = fragmentSpreads ?: emptyList(),
                inlineFragments = inlineFragments ?: emptyList(),
                fields = fields
            )
        )
    )
  } else {
    resolveFieldType(
        graphQLType = type,
        enums = context.enums,
        customTypeMap = context.customTypeMap,
        typesPackageName = context.typesPackageName
    )
  }
  return AST.ObjectType.Field(
      name = responseName,
      responseName = responseName,
      schemaName = fieldName,
      type = fieldType,
      description = description ?: "",
      isOptional = !type.endsWith("!") || isConditional,
      isDeprecated = isDeprecated ?: false,
      deprecationReason = deprecationReason ?: "",
      arguments = args?.associate { it.name to it.value.normalizeJsonValue(it.type) } ?: emptyMap(),
      conditions = normalizedConditions
  )
}

private fun Field.toASTObjectField(context: CodeGenContext): AST.ObjectType.Field {
  val typeRef = context.addObjectType(
      type = responseName,
      schemaType = type,
      fragmentSpreads = fragmentSpreads ?: emptyList(),
      inlineFragments = inlineFragments ?: emptyList(),
      fields = fields ?: emptyList()
  )
  return AST.ObjectType.Field(
      name = responseName,
      responseName = responseName,
      schemaName = fieldName,
      type = AST.FieldType.Object(typeRef),
      description = description ?: "",
      isOptional = !type.endsWith("!") || isConditional || (inlineFragments?.isNotEmpty() == true),
      isDeprecated = isDeprecated ?: false,
      deprecationReason = deprecationReason ?: "",
      arguments = args?.associate { it.name to it.value.normalizeJsonValue(it.type) } ?: emptyMap(),
      conditions = normalizedConditions
  )
}

private fun List<Fragment>.toFragmentsObjectField(
    fragmentsPackage: String,
    isOptional: (typeCondition: String) -> Boolean
): Pair<AST.ObjectType.Field?, AST.ObjectType?> {
  if (isEmpty()) {
    return null to null
  }
  val type = AST.ObjectType(
      name = "Fragments",
      fields = map { fragment ->
        AST.ObjectType.Field(
            name = fragment.fragmentName,
            responseName = fragment.fragmentName,
            schemaName = fragment.fragmentName,
            type = AST.FieldType.Object(AST.TypeRef(name = fragment.fragmentName, packageName = fragmentsPackage)),
            description = "",
            isOptional = isOptional(fragment.typeCondition),
            isDeprecated = false,
            deprecationReason = "",
            arguments = emptyMap(),
            conditions = fragment.possibleTypes.map { AST.ObjectType.Field.Condition.Type(it) }
        )
      },
      fragmentsType = null
  )
  val field = AST.ObjectType.Field(
      name = type.name.decapitalize(),
      responseName = "__typename",
      schemaName = "__typename",
      type = AST.FieldType.Fragments(
          name = type.name,
          fields = type.fields.map { field ->
            AST.FieldType.Fragments.Field(
                name = field.name,
                type = (field.type as AST.FieldType.Object).typeRef,
                isOptional = field.isOptional
            )
          }
      ),
      description = "",
      isOptional = false,
      isDeprecated = false,
      deprecationReason = "",
      arguments = emptyMap(),
      conditions = emptyList()
  )
  return field to type
}

private fun CodeGenContext.addInlineFragmentType(inlineFragment: InlineFragment): AST.TypeRef {
  return addObjectType(
      type = "As${inlineFragment.typeCondition}",
      schemaType = inlineFragment.typeCondition,
      fragmentSpreads = inlineFragment.fragmentSpreads ?: emptyList(),
      inlineFragments = emptyList(),
      fields = inlineFragment.fields
  )
}

private fun Map<InlineFragment, AST.TypeRef>.toInlineObjectFields(
    isOptional: (typeCondition: String) -> Boolean
): List<AST.ObjectType.Field> {
  return map { (inlineFragment, typeRef) ->
    AST.ObjectType.Field(
        name = typeRef.name.decapitalize(),
        responseName = "__typename",
        schemaName = "__typename",
        type = AST.FieldType.InlineFragment(typeRef),
        description = "",
        isOptional = isOptional(inlineFragment.typeCondition),
        isDeprecated = false,
        deprecationReason = "",
        arguments = emptyMap(),
        conditions = inlineFragment.possibleTypes?.map { AST.ObjectType.Field.Condition.Type(it) }
            ?: inlineFragment.typeCondition.let { listOf(AST.ObjectType.Field.Condition.Type(it)) }
    )
  }
}

private fun CodeGenContext.addObjectType(
    type: String,
    schemaType: String,
    fragmentSpreads: List<String>,
    inlineFragments: List<InlineFragment>,
    fields: List<Field>
): AST.TypeRef {
  val (fragmentsField, fragmentsObjectType) = fragmentSpreads
      .map { fragments[it] ?: throw IllegalArgumentException("Unable to find fragment definition: $it") }
      .toFragmentsObjectField(fragmentsPackage = fragmentsPackage, isOptional = { it != schemaType.removeSuffix("!") })

  val inlineFragmentFields = inlineFragments
      .associate { it to addInlineFragmentType(it) }
      .toInlineObjectFields { it != schemaType.removeSuffix("!") }

  return addObjectType(type) {
    AST.ObjectType(
        name = type.removeSuffix("!").removePrefix("[").removeSuffix("]").removeSuffix("!"),
        fields = (fields.map { it.toASTField(this) } + inlineFragmentFields).let {
          if (fragmentsField != null) it + fragmentsField else it
        },
        fragmentsType = fragmentsObjectType
    )
  }
}

private fun Field.toASTScalarField(context: CodeGenContext): AST.ObjectType.Field {
  return AST.ObjectType.Field(
      name = responseName,
      responseName = responseName,
      schemaName = fieldName,
      type = resolveFieldType(
          graphQLType = type,
          enums = context.enums,
          customTypeMap = context.customTypeMap,
          typesPackageName = context.typesPackageName
      ),
      description = description ?: "",
      isOptional = !type.endsWith("!") || isConditional,
      isDeprecated = isDeprecated ?: false,
      deprecationReason = deprecationReason ?: "",
      arguments = args?.associate { it.name to it.value.normalizeJsonValue(it.type) } ?: emptyMap(),
      conditions = normalizedConditions
  )
}

private fun Fragment.toASTFragment(context: CodeGenContext): AST.FragmentType {
  val inlineFragmentFields = inlineFragments
      .associate { it to context.addInlineFragmentType(it) }
      .toInlineObjectFields { true }
  return AST.FragmentType(
      name = fragmentName,
      definition = source,
      possibleTypes = possibleTypes,
      fields = fields.map { it.toASTField(context) } + inlineFragmentFields,
      nestedObjects = context.objectTypes
  )
}

private val Operation.astOperationType
  get() = when {
    isQuery() -> AST.OperationType.Type.QUERY
    isMutation() -> AST.OperationType.Type.MUTATION
    isSubscription() -> AST.OperationType.Type.SUBSCRIPTION
    else -> throw IllegalArgumentException("Unsupported GraphQL operation type: ${operationType}")
  }

private val Field.isObjectTypeField: Boolean
  get() = isNonScalar()

private val Field.isArrayTypeField: Boolean
  get() = type.removeSuffix("!").let { it.startsWith('[') && it.endsWith(']') }

private val AST.FieldType.isCustomType: Boolean
  get() = this is AST.FieldType.Scalar.Custom || (this as? AST.FieldType.Array)?.rawType?.isCustomType ?: false

private fun resolveFieldType(
    graphQLType: String,
    enums: List<AST.EnumType>,
    customTypeMap: Map<String, String>,
    typesPackageName: String,
    fallbackType: (graphQLType: String) -> AST.TypeRef = {
      throw IllegalArgumentException("Unknown GraphQL type: $it")
    }
): AST.FieldType {
  val isGraphQLArrayType = graphQLType.removeSuffix("!").let { it.startsWith('[') && it.endsWith(']') }
  if (isGraphQLArrayType) {
    return AST.FieldType.Array(
        resolveFieldType(
            graphQLType = graphQLType.removeSuffix("!").removePrefix("[").removeSuffix("]").removeSuffix("!"),
            enums = enums,
            customTypeMap = customTypeMap,
            typesPackageName = typesPackageName,
            fallbackType = fallbackType
        )
    )
  } else {
    return when (ScalarType.forName(graphQLType.removeSuffix("!"))) {
      is ScalarType.STRING -> AST.FieldType.Scalar.String
      is ScalarType.INT -> AST.FieldType.Scalar.Int
      is ScalarType.BOOLEAN -> AST.FieldType.Scalar.Boolean
      is ScalarType.FLOAT -> AST.FieldType.Scalar.Float
      else -> when {
        enums.find { it.name == graphQLType.removeSuffix("!") } != null -> AST.FieldType.Scalar.Enum(
            AST.TypeRef(
                name = graphQLType.removeSuffix("!"),
                packageName = typesPackageName
            )
        )
        customTypeMap.containsKey(graphQLType.removeSuffix("!")) -> AST.FieldType.Scalar.Custom(
            graphQLType = graphQLType.removeSuffix("!"),
            mappedType = customTypeMap[graphQLType.removeSuffix("!")]!!,
            customEnumType = AST.TypeRef(
                name = "CustomType",
                packageName = typesPackageName
            )
        )
        else -> AST.FieldType.Object(fallbackType(graphQLType.removeSuffix("!")))
      }
    }
  }
}

private fun Any.normalizeJsonValue(graphQLType: String): Any {
  return when (this) {
    is Number -> {
      val scalarType = ScalarType.forName(graphQLType.removeSuffix("!"))
      when (scalarType) {
        is ScalarType.INT -> toInt()
        is ScalarType.FLOAT -> toDouble()
        else -> this
      }
    }
    is Boolean, is Map<*, *> -> this
    is List<*> -> this.map {
      it!!.normalizeJsonValue(graphQLType.removeSuffix("!").removePrefix("[").removeSuffix("]"))
    }
    else -> toString()
  }
}

private val Field.normalizedConditions: List<AST.ObjectType.Field.Condition>
  get() {
    return if (isConditional) {
      conditions?.filter { it.kind == Condition.Kind.BOOLEAN.rawValue }?.map {
        AST.ObjectType.Field.Condition.Directive(
            variableName = it.variableName,
            inverted = it.inverted
        )
      } ?: emptyList()
    } else {
      emptyList()
    }
  }

private class CodeGenContext(
    val customTypeMap: Map<String, String>,
    val enums: List<AST.EnumType>,
    val typesPackageName: String,
    val fragmentsPackage: String,
    val fragments: Map<String, Fragment>
) {
  private val reservedObjectTypeRefs = HashSet<AST.TypeRef>()
  private val objectTypeContainer: MutableMap<AST.TypeRef, AST.ObjectType> = LinkedHashMap()
  val objectTypes: Map<AST.TypeRef, AST.ObjectType> = objectTypeContainer

  fun addObjectType(typeName: String, packageName: String = "", provideObjectType: () -> AST.ObjectType): AST.TypeRef {
    val uniqueTypeRef = reservedObjectTypeRefs.generateUniqueTypeRef(typeName = typeName, packageName = packageName)
    reservedObjectTypeRefs.add(uniqueTypeRef)
    objectTypeContainer[uniqueTypeRef] = provideObjectType()
    return uniqueTypeRef
  }

  private fun Set<AST.TypeRef>.generateUniqueTypeRef(typeName: String, packageName: String): AST.TypeRef {
    var index = 0
    var uniqueTypeRef = AST.TypeRef(name = typeName, packageName = packageName)
    while (find { it.name.toLowerCase() == uniqueTypeRef.name.toLowerCase() } != null) {
      uniqueTypeRef = AST.TypeRef(name = "${uniqueTypeRef.name}${++index}", packageName = packageName)
    }
    return uniqueTypeRef
  }
}