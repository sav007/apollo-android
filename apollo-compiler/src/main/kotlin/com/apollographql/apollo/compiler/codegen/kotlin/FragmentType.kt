package com.apollographql.apollo.compiler.codegen.kotlin

import com.apollographql.apollo.api.GraphqlFragment
import com.apollographql.apollo.api.Input
import com.apollographql.apollo.compiler.ast.AST
import com.squareup.kotlinpoet.*
import com.squareup.kotlinpoet.ParameterizedTypeName.Companion.parameterizedBy

fun AST.FragmentType.toTypeSpec(): TypeSpec {
  return TypeSpec.classBuilder(name.capitalize())
      .addModifiers(KModifier.DATA)
      .addSuperinterface(GraphqlFragment::class.java)
      .addAnnotation(Annotations.generatedByApollo)
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
          .addProperty(PropertySpec.builder("FRAGMENT_DEFINITION", String::class)
              .initializer("%S", definition)
              .build()
          )
          .addProperty(PropertySpec.builder("POSSIBLE_TYPES",
              Array<String>::class.asClassName().parameterizedBy(String::class.asClassName()))
              .initializer(possibleTypesInitializer)
              .build()
          )
          .addFunction(fields.toMapperFun(ClassName.bestGuess(name.capitalize())))
          .build()
      )
      .addTypes(nestedObjects.map { (ref, type) -> type.toTypeSpec(ref.name) })
      .addFunction(fields.toMarshallerFun().toBuilder().addModifiers(KModifier.OVERRIDE).build())
      .build()
}

private val AST.FragmentType.possibleTypesInitializer: CodeBlock
  get() {
    return possibleTypes.foldIndexed(CodeBlock.builder().add("arrayOf(")) { index, builder, possibleType ->
      if (index > 0) {
        builder.add(", ")
      }
      builder.add("%S", possibleType)
    }.add(")").build()
  }
