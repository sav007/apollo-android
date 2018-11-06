package com.apollographql.apollo.compiler.codegen.kotlin

import com.apollographql.apollo.api.ScalarType
import com.squareup.kotlinpoet.*
import com.squareup.kotlinpoet.ParameterizedTypeName.Companion.parameterizedBy

fun generateCustomTypeSpec(customTypes: Map<String, String>): TypeSpec {
  return TypeSpec.enumBuilder("CustomType")
      .addAnnotation(Annotations.generatedByApollo)
      .addSuperinterface(ScalarType::class.java)
      .apply {
        customTypes.forEach { (schemaType, customType) ->
          addEnumConstant(schemaType.toUpperCase(), TypeSpec.anonymousClassBuilder()
              .addFunction(FunSpec.builder("typeName")
                  .addModifiers(KModifier.OVERRIDE)
                  .returns(String::class)
                  .addStatement("return %S", schemaType)
                  .build()
              )
              .addFunction(FunSpec.builder("javaType")
                  .returns(Class::class.asClassName().parameterizedBy(WildcardTypeName.STAR))
                  .addModifiers(KModifier.OVERRIDE)
                  .addStatement("return %L::class.java", customType)
                  .build()
              )
              .build()
          )
        }
      }
      .build()
}