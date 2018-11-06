package com.example.fragments_with_type_condition.fragment

import com.apollographql.apollo.api.GraphqlFragment
import com.apollographql.apollo.api.ResponseField
import com.apollographql.apollo.api.ResponseFieldMarshaller
import com.apollographql.apollo.api.ResponseReader
import javax.annotation.Generated
import kotlin.Array
import kotlin.String

/**
 * @param name What others call this droid@param primaryFunction This droid's primary function */
@Generated("Apollo GraphQL")
data class DroidDetails(
    val __typename: String,
    val name: String,
    val primaryFunction: String?
) : GraphqlFragment {
    override fun marshaller(): ResponseFieldMarshaller = ResponseFieldMarshaller {
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

        val FRAGMENT_DEFINITION: String = """
                |fragment DroidDetails on Droid {
                |  __typename
                |  name
                |  primaryFunction
                |}
                """.trimMargin()

        val POSSIBLE_TYPES: Array<String> = arrayOf("Droid")

        operator fun invoke(reader: ResponseReader): DroidDetails {
            val __typename = reader.readString(RESPONSE_FIELDS[0])
            val name = reader.readString(RESPONSE_FIELDS[1])
            val primaryFunction = reader.readString(RESPONSE_FIELDS[2])
            return DroidDetails(
                __typename = __typename,
                name = name,
                primaryFunction = primaryFunction
            )
        }
    }
}
