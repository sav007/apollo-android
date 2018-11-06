package com.apollographql.apollo.compiler.codegen.kotlin

import com.apollographql.apollo.compiler.ast.AST
import com.squareup.kotlinpoet.*

fun AST.EnumType.toTypeSpec(): TypeSpec {
  return TypeSpec.enumBuilder(name)
      .apply {
        if (description.isNotBlank()) {
          addKdoc("%L", description)
        }
      }
      .addAnnotation(Annotations.generatedByApollo)
      .primaryConstructor(FunSpec.constructorBuilder()
          .addParameter("rawValue", String::class)
          .build()
      )
      .addProperty(PropertySpec.builder("rawValue", String::class)
          .initializer("rawValue")
          .build())
      .apply {
        values.forEach { enumValue ->
          addEnumConstant(enumValue.value.toUpperCase(), TypeSpec.anonymousClassBuilder()
              .apply { if (enumValue.description.isNotBlank()) addKdoc("%L", enumValue.description) }
              .apply {
                if (enumValue.isDeprecated) addAnnotation(Annotations.deprecated(enumValue.deprecationReason))
              }
              .addSuperclassConstructorParameter("%S", enumValue.value)
              .build()
          )
        }
        addEnumConstant("UNKNOWN__", TypeSpec.anonymousClassBuilder()
            .addKdoc("%S", "Auto generated constant for unknown enum values")
            .addSuperclassConstructorParameter("%S", "UNKNOWN__")
            .build()
        )
      }
      .addType(TypeSpec.companionObjectBuilder()
          .addFunction(FunSpec.builder("safeValueOf")
              .addAnnotation(JvmStatic::class)
              .addParameter("rawValue", String::class)
              .returns(ClassName("", name))
              .addCode("return values().find { it.rawValue == rawValue } ?: UNKNOWN__")
              .build()
          )
          .build()
      )
      .build()
}