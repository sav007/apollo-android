package com.example.input_object_type

import com.apollographql.apollo.api.InputFieldMarshaller
import com.apollographql.apollo.api.InputFieldWriter
import com.apollographql.apollo.api.Mutation
import com.apollographql.apollo.api.Operation
import com.apollographql.apollo.api.OperationName
import com.apollographql.apollo.api.ResponseField
import com.apollographql.apollo.api.ResponseFieldMapper
import com.apollographql.apollo.api.ResponseFieldMarshaller
import com.apollographql.apollo.api.ResponseReader
import com.example.input_object_type.type.Episode
import com.example.input_object_type.type.ReviewInput
import java.io.IOException
import javax.annotation.Generated
import kotlin.Any
import kotlin.Array
import kotlin.Int
import kotlin.String
import kotlin.collections.Map
import kotlin.jvm.Throws

@Generated("Apollo GraphQL")
data class TestQueryMutation(val ep: Episode, val review: ReviewInput) : Mutation<TestQueryMutation.Data, TestQueryMutation.Data, Operation.Variables> {
    @Transient
    private val variables: Operation.Variables = object : Operation.Variables() {
        override fun valueMap(): Map<String, Any?> = mutableMapOf<String, Any?>().apply {
            this["ep"] = ep
            this["review"] = review
        }

        override fun marshaller(): InputFieldMarshaller = object : InputFieldMarshaller {
            @Throws(IOException::class)
            override fun marshal(writer: InputFieldWriter) {
                writer.writeString("ep", ep.value.rawValue)
                writer.writeObject("review", review.marshaller())
            }
        }
    }

    override fun operationId(): String = OPERATION_ID
    override fun queryDocument(): String = QUERY_DOCUMENT
    override fun wrapData(data: TestQueryMutation.Data): TestQueryMutation.Data = data
    override fun variables(): Operation.Variables = variables
    override fun name(): OperationName = OPERATION_NAME
    override fun responseFieldMapper(): ResponseFieldMapper<TestQueryMutation.Data> = ResponseFieldMapper {
        TestQueryMutation.Data(it)
    }

    /**
     * @param stars The number of stars this review gave, 1-5@param commentary Comment about the movie */
    data class CreateReview(
        val __typename: String,
        val stars: Int,
        val commentary: String?
    ) {
        fun marshaller(): ResponseFieldMarshaller = ResponseFieldMarshaller {
            it.writeString(RESPONSE_FIELDS[0], __typename)
            it.writeInt(RESPONSE_FIELDS[1], stars)
            it.writeString(RESPONSE_FIELDS[2], commentary)
        }

        companion object {
            private val RESPONSE_FIELDS: Array<ResponseField> = arrayOf(
                        ResponseField.forString("__typename", "__typename", null, false, null),
                        ResponseField.forInt("stars", "stars", null, false, null),
                        ResponseField.forString("commentary", "commentary", null, true, null)
                    )

            operator fun invoke(reader: ResponseReader): CreateReview {
                val __typename = reader.readString(RESPONSE_FIELDS[0])
                val stars = reader.readInt(RESPONSE_FIELDS[1])
                val commentary = reader.readString(RESPONSE_FIELDS[2])
                return CreateReview(
                    __typename = __typename,
                    stars = stars,
                    commentary = commentary
                )
            }
        }
    }

    data class Data(val createReview: CreateReview?) : Operation.Data {
        override fun marshaller(): ResponseFieldMarshaller = ResponseFieldMarshaller {
            it.writeObject(RESPONSE_FIELDS[0], createReview?.marshaller())
        }

        companion object {
            private val RESPONSE_FIELDS: Array<ResponseField> = arrayOf(
                        ResponseField.forObject("createReview", "createReview", mapOf<String, Any>(
                            "episode" to mapOf<String, Any>(
                                "kind" to "Variable",
                                "variableName" to "ep"),
                            "review" to mapOf<String, Any>(
                                "kind" to "Variable",
                                "variableName" to "review")), true, null)
                    )

            operator fun invoke(reader: ResponseReader): Data {
                val createReview = reader.readObject<CreateReview>(RESPONSE_FIELDS[0]) {
                    CreateReview(it)
                }

                return Data(
                    createReview = createReview
                )
            }
        }
    }

    companion object {
        val OPERATION_DEFINITION: String = """
                |mutation TestQuery(${'$'}ep: Episode!, ${'$'}review: ReviewInput!) {
                |  createReview(episode: ${'$'}ep, review: ${'$'}review) {
                |    __typename
                |    stars
                |    commentary
                |  }
                |}
                """.trimMargin()

        const val OPERATION_ID: String =
                "557e9010a4f6274a5409cc73de928653c878c931099afa98357c530df729a448"

        val QUERY_DOCUMENT: String = """
                |mutation TestQuery(${'$'}ep: Episode!, ${'$'}review: ReviewInput!) {
                |  createReview(episode: ${'$'}ep, review: ${'$'}review) {
                |    __typename
                |    stars
                |    commentary
                |  }
                |}
                """.trimMargin()

        val OPERATION_NAME: OperationName = OperationName { "TestQueryMutation" }
    }
}
