package com.apollographql.apollo.compiler.codegen.kotlin

import com.apollographql.apollo.api.*
import com.apollographql.apollo.compiler.ast.AST
import com.squareup.kotlinpoet.*
import com.squareup.kotlinpoet.ParameterizedTypeName.Companion.parameterizedBy
import com.squareup.kotlinpoet.jvm.throws
import java.io.IOException

fun AST.OperationType.toTypeSpec(): TypeSpec {
  return TypeSpec.classBuilder(name.capitalize())
      .addAnnotation(Annotations.generatedByApollo)
      .addSuperinterface(
          when (type) {
            AST.OperationType.Type.QUERY -> Query::class.asClassName()
            AST.OperationType.Type.MUTATION -> Mutation::class.asClassName()
            AST.OperationType.Type.SUBSCRIPTION -> Subscription::class.asClassName()
          }.parameterizedBy(data.toTypeName(), data.toTypeName(), Operation.Variables::class.asClassName())
      )
      .applyIf(variables.fields.isNotEmpty()) {
        addModifiers(KModifier.DATA)
        primaryConstructor(FunSpec.constructorBuilder()
            .addParameters(variables.fields.map { variable ->
              val typeName = variable.type.toTypeName()
              ParameterSpec.builder(
                  name = variable.name.decapitalize(),
                  type = if (variable.isOptional) Input::class.asClassName().parameterizedBy(typeName) else typeName
              ).build()
            })
            .build()
        )
        addProperties(variables.fields.map { variable ->
          val typeName = variable.type.toTypeName()
          PropertySpec.builder(
              name = variable.name.decapitalize(),
              type = if (variable.isOptional) Input::class.asClassName().parameterizedBy(typeName) else typeName
          )
              .initializer(variable.name.decapitalize())
              .build()
        })
        addProperty(PropertySpec.builder("variables", Operation.Variables::class)
            .addModifiers(KModifier.PRIVATE)
            .addAnnotation(ClassName.bestGuess("Transient"))
            .initializer("%L", TypeSpec.anonymousClassBuilder()
                .superclass(Operation.Variables::class)
                .addFunction(FunSpec.builder("valueMap")
                    .addModifiers(KModifier.OVERRIDE)
                    .returns(Map::class.asClassName().parameterizedBy(String::class.asClassName(),
                        Any::class.asClassName().asNullable()))
                    .beginControlFlow("return mutableMapOf<%T, %T>().apply", String::class,
                        Any::class.asClassName().asNullable())
                    .addCode(
                        variables.fields.map { variable ->
                          if (variable.isOptional) {
                            CodeBlock.of("if (%L.defined) this[%S] = %L.value", variable.name.decapitalize(),
                                variable.name,
                                variable.name.decapitalize())
                          } else {
                            CodeBlock.of("this[%S] = %L", variable.name, variable.name.decapitalize())
                          }
                        }.joinToCode(separator = "\n", suffix = "\n")
                    )
                    .endControlFlow()
                    .build()
                )
                .addFunction(FunSpec.builder("marshaller")
                    .returns(InputFieldMarshaller::class)
                    .addModifiers(KModifier.OVERRIDE)
                    .addStatement("return %L", TypeSpec.anonymousClassBuilder()
                        .addSuperinterface(InputFieldMarshaller::class)
                        .addFunction(FunSpec.builder("marshal")
                            .addModifiers(KModifier.OVERRIDE)
                            .throws(IOException::class)
                            .addParameter(ParameterSpec.builder("writer", InputFieldWriter::class.java).build())
                            .apply { variables.fields.forEach { addCode(it.writeCodeBlock()) } }
                            .build()
                        ).build()
                    ).build()
                )
                .build()
            )
            .build()
        )
      }
      .addFunction(FunSpec.builder("operationId")
          .addModifiers(KModifier.OVERRIDE)
          .returns(String::class)
          .addCode("return OPERATION_ID")
          .build()
      )
      .addFunction(FunSpec.builder("queryDocument")
          .addModifiers(KModifier.OVERRIDE)
          .returns(String::class)
          .addCode("return QUERY_DOCUMENT")
          .build()
      )
      .addFunction(FunSpec.builder("wrapData")
          .addModifiers(KModifier.OVERRIDE)
          .addParameter(ParameterSpec.builder("data", data.toTypeName()).build())
          .returns(data.toTypeName())
          .addCode("return data")
          .build()
      )
      .addFunction(FunSpec.builder("variables")
          .addModifiers(KModifier.OVERRIDE)
          .returns(Operation.Variables::class.asClassName())
          .apply {
            if (variables.fields.isNotEmpty()) {
              addCode("return variables")
            } else {
              addCode("return %T.EMPTY_VARIABLES", Operation::class)
            }
          }
          .build()
      )
      .addFunction(FunSpec.builder("name")
          .addModifiers(KModifier.OVERRIDE)
          .returns(OperationName::class)
          .addCode("return OPERATION_NAME")
          .build()
      )
      .addFunction(FunSpec.builder("responseFieldMapper")
          .addModifiers(KModifier.OVERRIDE)
          .returns(ResponseFieldMapper::class.asClassName().parameterizedBy(data.toTypeName()))
          .beginControlFlow("return %T {", ResponseFieldMapper::class)
          .addStatement("%T(it)", data.toTypeName())
          .endControlFlow()
          .build()
      )
      .addTypes(nestedObjects.map { (ref, type) ->
        if (ref == data) {
          type.toOperationDataTypeSpec(data.name.capitalize())
        } else {
          type.toTypeSpec(ref.name)
        }
      })
      .addType(TypeSpec.companionObjectBuilder()
          .addProperty(PropertySpec.builder("OPERATION_DEFINITION", String::class)
              .initializer("%S", definition)
              .build()
          )
          .addProperty(PropertySpec.builder("OPERATION_ID", String::class)
              .addModifiers(KModifier.CONST)
              .initializer("%S", operationId)
              .build()
          )
          .addProperty(PropertySpec.builder("QUERY_DOCUMENT", String::class)
              .initializer("%S", queryDocument)
              .build()
          )
          .addProperty(PropertySpec.builder("OPERATION_NAME", OperationName::class)
              .initializer("%T { %S }", OperationName::class, name)
              .build())
          .build()
      )
      .build()
}

private fun AST.ObjectType.toOperationDataTypeSpec(name: String): TypeSpec {
  return TypeSpec.classBuilder(name.capitalize())
      .addModifiers(KModifier.DATA)
      .addSuperinterface(Operation.Data::class)
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
      .addFunction(fields.toMarshallerFun().toBuilder().addModifiers(KModifier.OVERRIDE).build())
      .build()
}