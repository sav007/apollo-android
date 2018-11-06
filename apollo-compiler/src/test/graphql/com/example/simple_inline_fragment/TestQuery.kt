package com.example.simple_inline_fragment

import com.apollographql.apollo.api.Operation
import com.apollographql.apollo.api.OperationName
import com.apollographql.apollo.api.Query
import com.apollographql.apollo.api.ResponseField
import com.apollographql.apollo.api.ResponseFieldMapper
import com.apollographql.apollo.api.ResponseFieldMarshaller
import com.apollographql.apollo.api.ResponseReader
import javax.annotation.Generated
import kotlin.Array
import kotlin.Double
import kotlin.String

@Generated("Apollo GraphQL")
class TestQuery : Query<TestQuery.Data, TestQuery.Data, Operation.Variables> {
    override fun operationId(): String = OPERATION_ID
    override fun queryDocument(): String = QUERY_DOCUMENT
    override fun wrapData(data: TestQuery.Data): TestQuery.Data = data
    override fun variables(): Operation.Variables = Operation.EMPTY_VARIABLES
    override fun name(): OperationName = OPERATION_NAME
    override fun responseFieldMapper(): ResponseFieldMapper<TestQuery.Data> = ResponseFieldMapper {
        TestQuery.Data(it)
    }

    /**
     * @param name What this human calls themselves@param height Height in the preferred unit, default is meters */
    data class AsHuman(
        val __typename: String,
        val name: String,
        val height: Double?
    ) {
        fun marshaller(): ResponseFieldMarshaller = ResponseFieldMarshaller {
            it.writeString(RESPONSE_FIELDS[0], __typename)
            it.writeString(RESPONSE_FIELDS[1], name)
            it.writeDouble(RESPONSE_FIELDS[2], height)
        }

        companion object {
            private val RESPONSE_FIELDS: Array<ResponseField> = arrayOf(
                        ResponseField.forString("__typename", "__typename", null, false, null),
                        ResponseField.forString("name", "name", null, false, null),
                        ResponseField.forDouble("height", "height", null, true, null)
                    )

            operator fun invoke(reader: ResponseReader): AsHuman {
                val __typename = reader.readString(RESPONSE_FIELDS[0])
                val name = reader.readString(RESPONSE_FIELDS[1])
                val height = reader.readDouble(RESPONSE_FIELDS[2])
                return AsHuman(
                    __typename = __typename,
                    name = name,
                    height = height
                )
            }
        }
    }

    /**
     * @param name What others call this droid@param primaryFunction This droid's primary function */
    data class AsDroid(
        val __typename: String,
        val name: String,
        val primaryFunction: String?
    ) {
        fun marshaller(): ResponseFieldMarshaller = ResponseFieldMarshaller {
            it.writeString(RESPONSE_FIELDS[0], __typename)
            it.writeString(RESPONSE_FIELDS[1], name)
            it.writeString(RESPONSE_FIELDS[2], primaryFunction)
        }

        companion object {
            private val RESPONSE_FIELDS: Array<ResponseField> = arrayOf(
                        ResponseField.forString("__typename", "__typename", null, false, null),
                        ResponseField.forString("name", "name", null, false, null),
                        ResponseField.forString("primaryFunction", "primaryFunction", null, true, null)
                    )

            operator fun invoke(reader: ResponseReader): AsDroid {
                val __typename = reader.readString(RESPONSE_FIELDS[0])
                val name = reader.readString(RESPONSE_FIELDS[1])
                val primaryFunction = reader.readString(RESPONSE_FIELDS[2])
                return AsDroid(
                    __typename = __typename,
                    name = name,
                    primaryFunction = primaryFunction
                )
            }
        }
    }

    /**
     * @param name The name of the character */
    data class Hero(
        val __typename: String,
        val name: String,
        val asHuman: AsHuman?,
        val asDroid: AsDroid?
    ) {
        fun marshaller(): ResponseFieldMarshaller = ResponseFieldMarshaller {
            it.writeString(RESPONSE_FIELDS[0], __typename)
            it.writeString(RESPONSE_FIELDS[1], name)
            it.writeObject(RESPONSE_FIELDS[2], asHuman?.marshaller())
            it.writeObject(RESPONSE_FIELDS[3], asDroid?.marshaller())
        }

        companion object {
            private val RESPONSE_FIELDS: Array<ResponseField> = arrayOf(
                        ResponseField.forString("__typename", "__typename", null, false, null),
                        ResponseField.forString("name", "name", null, false, null),
                        ResponseField.forInlineFragment("__typename", "__typename", listOf("Human")),
                        ResponseField.forInlineFragment("__typename", "__typename", listOf("Droid"))
                    )

            operator fun invoke(reader: ResponseReader): Hero {
                val __typename = reader.readString(RESPONSE_FIELDS[0])
                val name = reader.readString(RESPONSE_FIELDS[1])
                val asHuman = reader.readConditional(RESPONSE_FIELDS[2]) { conditionalType, reader ->
                    AsHuman(reader)
                }

                val asDroid = reader.readConditional(RESPONSE_FIELDS[3]) { conditionalType, reader ->
                    AsDroid(reader)
                }

                return Hero(
                    __typename = __typename,
                    name = name,
                    asHuman = asHuman,
                    asDroid = asDroid
                )
            }
        }
    }

    data class Data(val hero: Hero?) : Operation.Data {
        override fun marshaller(): ResponseFieldMarshaller = ResponseFieldMarshaller {
            it.writeObject(RESPONSE_FIELDS[0], hero?.marshaller())
        }

        companion object {
            private val RESPONSE_FIELDS: Array<ResponseField> = arrayOf(
                        ResponseField.forObject("hero", "hero", null, true, null)
                    )

            operator fun invoke(reader: ResponseReader): Data {
                val hero = reader.readObject<Hero>(RESPONSE_FIELDS[0]) {
                    Hero(it)
                }

                return Data(
                    hero = hero
                )
            }
        }
    }

    companion object {
        val OPERATION_DEFINITION: String = """
                |query TestQuery {
                |  hero {
                |    __typename
                |    name
                |    ... on Human {
                |      height
                |    }
                |    ... on Droid {
                |      primaryFunction
                |    }
                |  }
                |}
                """.trimMargin()

        const val OPERATION_ID: String =
                "212ff92146147cf94d8e05687d2660c3ead0b054e92d86a52e24eb15880c6dfd"

        val QUERY_DOCUMENT: String = """
                |query TestQuery {
                |  hero {
                |    __typename
                |    name
                |    ... on Human {
                |      height
                |    }
                |    ... on Droid {
                |      primaryFunction
                |    }
                |  }
                |}
                """.trimMargin()

        val OPERATION_NAME: OperationName = OperationName { "TestQuery" }
    }
}
