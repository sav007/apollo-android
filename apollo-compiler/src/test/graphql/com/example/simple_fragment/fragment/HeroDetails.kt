package com.example.simple_fragment.fragment

import com.apollographql.apollo.api.GraphqlFragment
import com.apollographql.apollo.api.ResponseField
import com.apollographql.apollo.api.ResponseFieldMarshaller
import com.apollographql.apollo.api.ResponseReader
import javax.annotation.Generated
import kotlin.Array
import kotlin.String

/**
 * @param name The name of the character */
@Generated("Apollo GraphQL")
data class HeroDetails(
    val __typename: String,
    val name: String
) : GraphqlFragment {
    override fun marshaller(): ResponseFieldMarshaller = ResponseFieldMarshaller {
        it.writeString(RESPONSE_FIELDS[0], __typename)
        it.writeString(RESPONSE_FIELDS[1], name)
    }

    companion object {
        private val RESPONSE_FIELDS: Array<ResponseField> = arrayOf(
                    ResponseField.forString("__typename", "__typename", null, false, null),
                    ResponseField.forString("name", "name", null, false, null)
                )

        val FRAGMENT_DEFINITION: String = """
                |fragment HeroDetails on Character {
                |  __typename
                |  name
                |}
                """.trimMargin()

        val POSSIBLE_TYPES: Array<String> = arrayOf("Human", "Droid")

        operator fun invoke(reader: ResponseReader): HeroDetails {
            val __typename = reader.readString(RESPONSE_FIELDS[0])
            val name = reader.readString(RESPONSE_FIELDS[1])
            return HeroDetails(
                __typename = __typename,
                name = name
            )
        }
    }
}
