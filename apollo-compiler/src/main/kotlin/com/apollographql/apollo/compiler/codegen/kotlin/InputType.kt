package com.apollographql.apollo.compiler.codegen.kotlin

import com.apollographql.apollo.api.Input
import com.apollographql.apollo.api.InputFieldMarshaller
import com.apollographql.apollo.api.InputFieldWriter
import com.apollographql.apollo.api.InputType
import com.apollographql.apollo.compiler.ast.AST
import com.squareup.kotlinpoet.*
import com.squareup.kotlinpoet.ParameterizedTypeName.Companion.parameterizedBy
import com.squareup.kotlinpoet.jvm.throws
import java.io.IOException

fun AST.InputType.toTypeSpec(): TypeSpec {
  return TypeSpec.classBuilder(name.capitalize())
      .addAnnotation(Annotations.generatedByApollo)
      .apply { if (description.isNotBlank()) addKdoc("%L", description) }
      .addSuperinterface(InputType::class)
      .primaryConstructor(FunSpec.constructorBuilder()
          .addParameters(fields.map { it.toParameterSpec() })
          .build()
      )
      .addProperties(fields.map { it.toPropertySpec(CodeBlock.of(it.name.decapitalize())) })
      .addFunction(FunSpec.builder("marshaller")
          .returns(InputFieldMarshaller::class)
          .addModifiers(KModifier.OVERRIDE)
          .addStatement("return %L", TypeSpec.anonymousClassBuilder()
              .addSuperinterface(InputFieldMarshaller::class)
              .addFunction(FunSpec.builder("marshal")
                  .addModifiers(KModifier.OVERRIDE)
                  .throws(IOException::class)
                  .addParameter(ParameterSpec.builder("writer", InputFieldWriter::class.java).build())
                  .apply { fields.forEach { addCode(it.writeCodeBlock()) } }
                  .build()
              ).build()
          ).build()
      ).build()
}

private fun AST.InputType.Field.toParameterSpec(): ParameterSpec {
  val typeName = type.toTypeName()
  return ParameterSpec.builder(
      name = name.decapitalize(),
      type = if (isOptional) Input::class.asClassName().parameterizedBy(typeName) else typeName
  ).apply {
    if (isOptional) {
      defaultValue(
          CodeBlock.of("%T.optional(%L)", Input::class, defaultValue?.toDefaultValueCodeBlock(
              typeName = typeName,
              fieldType = type
          ))
      )
    }
  }.build()
}

private fun AST.InputType.Field.toPropertySpec(initializer: CodeBlock): PropertySpec {
  return PropertySpec.builder(
      name = name.decapitalize(),
      type = if (isOptional) Input::class.asClassName().parameterizedBy(type.toTypeName()) else type.toTypeName()
  )
      .apply { if (description.isNotBlank()) addKdoc("%L", description) }
      .apply { initializer(initializer) }
      .build()
}

fun AST.InputType.Field.writeCodeBlock(): CodeBlock {
  return when (type) {
    is AST.FieldType.Scalar -> when (type) {
      is AST.FieldType.Scalar.String -> CodeBlock.of("writer.writeString(%S, %L)\n", name, name.decapitalize())
      is AST.FieldType.Scalar.Int -> CodeBlock.of("writer.writeInt(%S, %L)\n", name, name.decapitalize())
      is AST.FieldType.Scalar.Boolean -> CodeBlock.of("writer.writeBoolean(%S, %L)\n", name, name.decapitalize())
      is AST.FieldType.Scalar.Float -> CodeBlock.of("writer.writeDouble(%S, %L)\n", name, name.decapitalize())
      is AST.FieldType.Scalar.Enum -> {
        if (isOptional) {
          CodeBlock.of("if (%L.defined) writer.writeString(%S, %L.value?.rawValue)\n", name.decapitalize(), name,
              name.decapitalize())
        } else {
          CodeBlock.of("writer.writeString(%S, %L.value.rawValue)\n", name, name.decapitalize())
        }
      }
      is AST.FieldType.Scalar.Custom -> CodeBlock.of("writer.writeCustom(%S, %T.%L, %L.value)\n", name,
          type.customEnumType.toTypeName(), type.graphQLType.toUpperCase(), name.decapitalize())
    }
    is AST.FieldType.Object -> {
      if (isOptional) {
        CodeBlock.of("if (%L.defined) writer.writeString(%S, %L.value?.marshaller())\n", name.decapitalize(), name,
            name.decapitalize())
      } else {
        CodeBlock.of("writer.writeObject(%S, %L.marshaller())\n", name, name.decapitalize())
      }
    }
    is AST.FieldType.Array -> {
      val codeBlockBuilder: CodeBlock.Builder = CodeBlock.Builder()
      if (isOptional) {
        codeBlockBuilder
            .beginControlFlow("if (%L.defined)", name.decapitalize())
            .add("writer.writeList(%S, %L.value?.let {\n", name, name.decapitalize())
            .indent()
            .add("%L", TypeSpec.anonymousClassBuilder()
                .addSuperinterface(InputFieldWriter.ListWriter::class)
                .addFunction(FunSpec.builder("write")
                    .addModifiers(KModifier.OVERRIDE)
                    .addParameter(
                        ParameterSpec.builder("listItemWriter", InputFieldWriter.ListItemWriter::class).build())
                    .beginControlFlow("it.foreach")
                    .addCode(type.rawType.writeListItems())
                    .endControlFlow()
                    .build()
                )
                .build()
            )
            .unindent()
            .add("\n} ?: null)\n")
            .endControlFlow()
            .build()
      } else {
        codeBlockBuilder
            .add("writer.writeList(%S, %L", name, TypeSpec.anonymousClassBuilder()
                .addSuperinterface(InputFieldWriter.ListWriter::class)
                .addFunction(FunSpec.builder("write")
                    .addModifiers(KModifier.OVERRIDE)
                    .addParameter(
                        ParameterSpec.builder("listItemWriter", InputFieldWriter.ListItemWriter::class).build())
                    .beginControlFlow("%L.foreach", name.decapitalize())
                    .addCode(type.rawType.writeListItems())
                    .endControlFlow()
                    .build()
                )
                .build()
            )
            .add(")\n")
            .build()
      }
    }
    else -> throw IllegalArgumentException("Unsupported input object field type: $type")
  }
}

private fun AST.FieldType.writeListItems(): CodeBlock {
  return when (this) {
    is AST.FieldType.Scalar -> when (this) {
      is AST.FieldType.Scalar.String -> CodeBlock.of("listItemWriter.writeString(it)\n")
      is AST.FieldType.Scalar.Int -> CodeBlock.of("listItemWriter.writeInt(it)\n")
      is AST.FieldType.Scalar.Boolean -> CodeBlock.of("listItemWriter.writeBoolean(it)\n")
      is AST.FieldType.Scalar.Float -> CodeBlock.of("listItemWriter.writeDouble(it)\n")
      is AST.FieldType.Scalar.Enum -> CodeBlock.of("listItemWriter.writeString(it?.rawValue)\n")
      is AST.FieldType.Scalar.Custom -> CodeBlock.of("listItemWriter.writeCustom(%T.%L, it)\n",
          customEnumType.toTypeName(), graphQLType.toUpperCase())
    }
    is AST.FieldType.Object -> CodeBlock.of("listItemWriter.writeObject(it.marshaller())\n")
    is AST.FieldType.Array -> CodeBlock.of("listItemWriter.writeList(%L)\n", TypeSpec.anonymousClassBuilder()
        .addSuperinterface(InputFieldWriter.ListWriter::class)
        .addFunction(FunSpec.builder("write")
            .addModifiers(KModifier.OVERRIDE)
            .addParameter(ParameterSpec.builder("listItemWriter", InputFieldWriter.ListItemWriter::class).build())
            .beginControlFlow("it.foreach")
            .addCode(rawType.writeListItems())
            .endControlFlow()
            .build()
        )
        .build()
    )
    else -> throw IllegalArgumentException("Unsupported input object field type: $this")
  }
}