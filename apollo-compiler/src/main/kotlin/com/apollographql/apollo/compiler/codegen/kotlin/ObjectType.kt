package com.apollographql.apollo.compiler.codegen.kotlin

import com.apollographql.apollo.api.ResponseField
import com.apollographql.apollo.api.ResponseFieldMarshaller
import com.apollographql.apollo.api.ResponseReader
import com.apollographql.apollo.compiler.ast.AST
import com.squareup.kotlinpoet.*
import com.squareup.kotlinpoet.ParameterizedTypeName.Companion.parameterizedBy

fun AST.ObjectType.toTypeSpec(name: String): TypeSpec {
  return TypeSpec.classBuilder(name.capitalize())
      .addModifiers(KModifier.DATA)
      .primaryConstructor(FunSpec.constructorBuilder()
          .addParameters(fields.map { field ->
            val typeName = field.type.toTypeName()
            ParameterSpec.builder(
                name = field.name.decapitalize(),
                type = if (field.isOptional) typeName.asNullable() else typeName
            ).build()
          })
          .build()
      )
      .addProperties(fields.map { it.toPropertySpec(CodeBlock.of(it.name.decapitalize())) })
      .addType(TypeSpec.companionObjectBuilder()
          .addProperty(fields.toResponseFieldPropertySpec())
          .addFunction(fields.toMapperFun(ClassName.bestGuess(name.capitalize())))
          .build()
      )
      .apply { if (fragmentsType != null) addType(fragmentsType.toFragmentsTypeSpec()) }
      .addFunction(fields.toMarshallerFun())
      .build()
}

fun AST.ObjectType.toFragmentsTypeSpec(alias: String? = null): TypeSpec {
  return TypeSpec.classBuilder(alias?.capitalize() ?: name.capitalize())
      .addModifiers(KModifier.DATA)
      .primaryConstructor(FunSpec.constructorBuilder()
          .addParameters(fields.map { field ->
            val typeName = field.type.toTypeName()
            ParameterSpec.builder(
                name = field.name.decapitalize(),
                type = if (field.isOptional) typeName.asNullable() else typeName
            ).build()
          })
          .build()
      )
      .addProperties(fields.map { it.toPropertySpec(CodeBlock.of(it.name.decapitalize())) })
      .addFunction(FunSpec.builder("marshaller")
          .returns(ResponseFieldMarshaller::class)
          .beginControlFlow("return %T", ResponseFieldMarshaller::class)
          .addCode(
              fields.map { field ->
                if (field.isOptional) {
                  CodeBlock.of("%L?.marshaller()?.marshal(it)", field.name.decapitalize())
                } else {
                  CodeBlock.of("%L.marshaller().marshal(it)", field.name.decapitalize())
                }
              }.joinToCode(separator = "\n", suffix = "\n")
          )
          .endControlFlow()
          .build()
      )
      .build()
}

fun AST.ObjectType.Field.toPropertySpec(initializer: CodeBlock): PropertySpec {
  return PropertySpec.builder(
      name = name.decapitalize(),
      type = if (isOptional) type.toTypeName().asNullable() else type.toTypeName()
  )
      .apply { if (description.isNotBlank()) addKdoc("%L", description) }
      .initializer(initializer)
      .build()
}

fun List<AST.ObjectType.Field>.toResponseFieldPropertySpec(): PropertySpec {
  val initializer = CodeBlock.builder()
      .add("arrayOf(\n")
      .indent()
      .add(map { field -> field.toResponseFieldInitializer() }.joinToCode(separator = ",\n", suffix = "\n"))
      .unindent()
      .add(")")
      .build()
  return PropertySpec.builder("RESPONSE_FIELDS",
      Array<ResponseField>::class.asClassName().parameterizedBy(ResponseField::class.asClassName()),
      KModifier.PRIVATE
  ).initializer(initializer).build()
}

fun AST.ObjectType.Field.toResponseFieldInitializer(): CodeBlock {
  val builder = CodeBlock.builder().add("%T.%L", ResponseField::class, when (type) {
    is AST.FieldType.Scalar -> when (type) {
      is AST.FieldType.Scalar.String -> "forString"
      is AST.FieldType.Scalar.Int -> "forInt"
      is AST.FieldType.Scalar.Boolean -> "forBoolean"
      is AST.FieldType.Scalar.Float -> "forDouble"
      is AST.FieldType.Scalar.Enum -> "forEnum"
      is AST.FieldType.Scalar.Custom -> "forCustomType"
    }
    is AST.FieldType.Fragments -> "forString"
    is AST.FieldType.Object -> "forObject"
    is AST.FieldType.InlineFragment -> "forInlineFragment"
    is AST.FieldType.Array -> "forList"
  })

  when {
    type is AST.FieldType.Scalar && type is AST.FieldType.Scalar.Custom -> {
      builder.add("(%S, %S, %L, %L, %T.%L, %L)", responseName, schemaName, arguments.toCode(), isOptional,
          type.customEnumType.toTypeName(), type.graphQLType.toUpperCase(), conditions.toCode())
    }
    type is AST.FieldType.InlineFragment -> {
      builder.add("(%S, %S, %L)", responseName, schemaName, conditions.toCode())
    }
    else -> {
      builder.add("(%S, %S, %L, %L, %L)", responseName, schemaName, arguments.toCode(), isOptional, conditions.toCode())
    }
  }

  return builder.build()
}

private fun List<AST.ObjectType.Field.Condition>.toCode(): CodeBlock? {
  return takeIf { isNotEmpty() }
      ?.map { it.toCode() }
      ?.joinToCode(prefix = "listOf(", separator = ", ", suffix = ")")
}

fun AST.ObjectType.Field.Condition.toCode(): CodeBlock {
  return when (this) {
    is AST.ObjectType.Field.Condition.Type -> CodeBlock.of("%S", type)
    is AST.ObjectType.Field.Condition.Directive -> CodeBlock.of("%T.booleanCondition(%S, %L)",
        ResponseField.Condition::class, variableName, inverted)
  }
}

fun List<AST.ObjectType.Field>.toMapperFun(responseTypeName: TypeName): FunSpec {
  val readFieldsCode = mapIndexed { index, field ->
    CodeBlock.of("val %L = %L", field.name.decapitalize(), field.type.readCode(
        reader = "reader",
        field = "RESPONSE_FIELDS[$index]"
    ))
  }.joinToCode(separator = "\n", suffix = "\n")
  val mapFieldsCode = map { field ->
    CodeBlock.of("%L = %L", field.name.decapitalize(), field.name.decapitalize())
  }.joinToCode(separator = ",\n", suffix = "\n")
  return FunSpec.builder("invoke")
      .addModifiers(KModifier.OPERATOR)
      .addParameter(ParameterSpec.builder("reader", ResponseReader::class).build())
      .returns(responseTypeName)
      .addCode(CodeBlock.builder()
          .add(readFieldsCode)
          .addStatement("return %T(", responseTypeName)
          .indent()
          .add(mapFieldsCode)
          .unindent()
          .addStatement(")")
          .build()
      )
      .build()
}

fun List<AST.ObjectType.Field>.toMarshallerFun(): FunSpec {
  val writeFieldsCode = foldIndexed(CodeBlock.builder()) { index, builder, field ->
    builder.addStatement("%L", field.writeCode(field = "RESPONSE_FIELDS[$index]"))
  }.build()
  return FunSpec.builder("marshaller")
      .returns(ResponseFieldMarshaller::class)
      .beginControlFlow("return %T", ResponseFieldMarshaller::class)
      .addCode(writeFieldsCode)
      .endControlFlow()
      .build()
}

fun AST.ObjectType.Field.writeCode(field: String): CodeBlock {
  return when (type) {
    is AST.FieldType.Scalar -> when (type) {
      is AST.FieldType.Scalar.String -> CodeBlock.of("it.writeString(%L, %L)", field, name.decapitalize())
      is AST.FieldType.Scalar.Int -> CodeBlock.of("it.writeInt(%L, %L)", field, name.decapitalize())
      is AST.FieldType.Scalar.Boolean -> CodeBlock.of("it.writeBoolean(%L, %L)", field, name.decapitalize())
      is AST.FieldType.Scalar.Float -> CodeBlock.of("it.writeDouble(%L, %L)", field, name.decapitalize())
      is AST.FieldType.Scalar.Enum -> {
        if (isOptional) {
          CodeBlock.of("it.writeString(%L, %L?.rawValue)", field, name.decapitalize())
        } else {
          CodeBlock.of("it.writeString(%L, %L.rawValue)", field, name.decapitalize())
        }
      }
      is AST.FieldType.Scalar.Custom -> CodeBlock.of("it.writeCustom(%L as %T, %L)", field,
          ResponseField.CustomTypeField::class, name.decapitalize())
    }
    is AST.FieldType.Object, is AST.FieldType.InlineFragment -> {
      if (isOptional) {
        CodeBlock.of("it.writeObject(%L, %L?.marshaller())", field, name.decapitalize())
      } else {
        CodeBlock.of("it.writeObject(%L, %L.marshaller())", field, name.decapitalize())
      }
    }
    is AST.FieldType.Array -> {
      CodeBlock.builder()
          .beginControlFlow("it.writeList(%L, %L) { value, listItemWriter ->", field, name.decapitalize())
          .add("@%T(%S)\n", Suppress::class, "NAME_SHADOWING")
          .beginControlFlow("value?.forEach { value ->")
          .add(type.rawType.writeListItemCode())
          .endControlFlow()
          .endControlFlow()
          .build()
    }
    is AST.FieldType.Fragments -> CodeBlock.of("%L.marshaller().marshal(it)", name.decapitalize())
  }
}

fun AST.FieldType.toTypeName(): TypeName = when (this) {
  is AST.FieldType.Scalar -> when (this) {
    AST.FieldType.Scalar.String -> String::class.asClassName()
    AST.FieldType.Scalar.Int -> INT
    AST.FieldType.Scalar.Boolean -> BOOLEAN
    AST.FieldType.Scalar.Float -> DOUBLE
    is AST.FieldType.Scalar.Enum -> ClassName(
        packageName = typeRef.packageName,
        simpleName = typeRef.name.capitalize()
    )
    is AST.FieldType.Scalar.Custom -> ClassName.bestGuess(mappedType)
  }
  is AST.FieldType.Fragments -> ClassName.bestGuess(name.capitalize())
  is AST.FieldType.Object -> ClassName(
      packageName = typeRef.packageName,
      simpleName = typeRef.name.capitalize()
  )
  is AST.FieldType.InlineFragment -> ClassName(
      packageName = typeRef.packageName,
      simpleName = typeRef.name.capitalize()
  )
  is AST.FieldType.Array -> List::class.asClassName().parameterizedBy(rawType.toTypeName())
}

fun AST.FieldType.readCode(reader: String, field: String): CodeBlock {
  return when (this) {
    is AST.FieldType.Scalar -> when (this) {
      is AST.FieldType.Scalar.String -> CodeBlock.of("%L.readString(%L)", reader, field)
      is AST.FieldType.Scalar.Int -> CodeBlock.of("%L.readInt(%L)", reader, field)
      is AST.FieldType.Scalar.Boolean -> CodeBlock.of("%L.readBoolean(%L)", reader, field)
      is AST.FieldType.Scalar.Float -> CodeBlock.of("%L.readDouble(%L)", reader, field)
      is AST.FieldType.Scalar.Enum -> CodeBlock.of("%T.safeValueOf(%L.readString(%L))", typeRef.toTypeName(), reader,
          field)
      is AST.FieldType.Scalar.Custom -> if (field.isNotEmpty()) {
        CodeBlock.of("%L.readCustomType<%T>(%L as %T)", reader, ClassName.bestGuess(mappedType), field,
            ResponseField.CustomTypeField::class)
      } else {
        CodeBlock.of("%L.readCustomType<%T>(%T.%L)", reader, ClassName.bestGuess(mappedType),
            customEnumType.toTypeName(), graphQLType.toUpperCase())
      }
    }
    is AST.FieldType.Object -> {
      val fieldCode = field.takeIf { it.isNotEmpty() }?.let { CodeBlock.of("(%L)", it) } ?: CodeBlock.of("")
      CodeBlock.builder()
          .beginControlFlow("%L.readObject<%T>%L", reader, typeRef.toTypeName(), fieldCode)
          .addStatement("%T(it)", typeRef.toTypeName())
          .endControlFlow()
          .build()
    }
    is AST.FieldType.Array -> {
      CodeBlock.builder()
          .apply {
            if (field.isEmpty()) {
              beginControlFlow("%L.readList<%T>", reader, rawType.toTypeName())
            } else {
              beginControlFlow("%L.readList<%T>(%L)", reader, rawType.toTypeName(), field)
            }
          }
          .add(rawType.readCode(reader = "it", field = ""))
          .add("\n")
          .endControlFlow()
          .build()
    }
    is AST.FieldType.Fragments -> {
      CodeBlock.builder()
          .beginControlFlow("%L.readConditional(%L) { conditionalType, reader ->", reader, field)
          .add(
              fields.map { field ->
                CodeBlock.of("val %L = if (%T.POSSIBLE_TYPES.contains(conditionalType)) %T(reader) else null",
                    field.name.decapitalize(), field.type.toTypeName(), field.type.toTypeName())
              }.joinToCode(separator = "\n", suffix = "\n")
          )
          .addStatement("%L(", name.capitalize())
          .indent()
          .add(
              fields.map { field ->
                if (field.isOptional) {
                  CodeBlock.of("%L = %L", field.name.decapitalize(), field.name.decapitalize())
                } else {
                  CodeBlock.of("%L = %L!!", field.name.decapitalize(), field.name.decapitalize())
                }

              }.joinToCode(separator = ",\n", suffix = "\n")
          )
          .unindent()
          .addStatement(")")
          .endControlFlow()
          .build()
    }
    is AST.FieldType.InlineFragment -> {
      CodeBlock.builder()
          .beginControlFlow("%L.readConditional(%L) { conditionalType, reader ->", reader, field)
          .addStatement("%T(reader)", typeRef.toTypeName())
          .endControlFlow()
          .build()
    }
  }
}

fun AST.FieldType.writeListItemCode(): CodeBlock {
  return when (this) {
    is AST.FieldType.Scalar -> when (this) {
      is AST.FieldType.Scalar.String -> CodeBlock.of("listItemWriter.writeString(value)")
      is AST.FieldType.Scalar.Int -> CodeBlock.of("listItemWriter.writeInt(value)")
      is AST.FieldType.Scalar.Boolean -> CodeBlock.of("listItemWriter.writeBoolean(value)")
      is AST.FieldType.Scalar.Float -> CodeBlock.of("listItemWriter.writeDouble(value)")
      is AST.FieldType.Scalar.Enum -> CodeBlock.of("listItemWriter.writeString(value)")
      is AST.FieldType.Scalar.Custom -> CodeBlock.of("listItemWriter.writeCustom(%T.%L, value)",
          customEnumType.toTypeName(), graphQLType.toUpperCase())
    }
    is AST.FieldType.Object -> CodeBlock.of("listItemWriter.writeObject(value?.marshaller())", toTypeName())
    is AST.FieldType.Array -> {
      CodeBlock.builder()
          .beginControlFlow("listItemWriter.writeList(value) { value, listItemWriter ->",
              List::class.asClassName().parameterizedBy(rawType.toTypeName()))
          .beginControlFlow("(value)?.forEach { value ->",
              List::class.asClassName().parameterizedBy(rawType.toTypeName()))
          .add(rawType.writeListItemCode())
          .endControlFlow()
          .endControlFlow()
          .build()
    }
    else -> throw IllegalArgumentException("Unsupported field type: $this")
  }
}