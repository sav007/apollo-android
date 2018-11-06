package com.example.union_inline_fragments

import com.apollographql.apollo.api.Operation
import com.apollographql.apollo.api.OperationName
import com.apollographql.apollo.api.Query
import com.apollographql.apollo.api.ResponseField
import com.apollographql.apollo.api.ResponseFieldMapper
import com.apollographql.apollo.api.ResponseFieldMarshaller
import com.apollographql.apollo.api.ResponseReader
import com.example.union_inline_fragments.type.CustomType
import com.example.union_inline_fragments.type.Episode
import javax.annotation.Generated
import kotlin.Array
import kotlin.String
import kotlin.Suppress
import kotlin.collections.List

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
     * @param firstAppearsIn The movie this character first appears in */
    data class Friends(
        val __typename: String,
        val firstAppearsIn: Episode
    ) {
        fun marshaller(): ResponseFieldMarshaller = ResponseFieldMarshaller {
            it.writeString(RESPONSE_FIELDS[0], __typename)
            it.writeString(RESPONSE_FIELDS[1], firstAppearsIn.rawValue)
        }

        companion object {
            private val RESPONSE_FIELDS: Array<ResponseField> = arrayOf(
                        ResponseField.forString("__typename", "__typename", null, false, null),
                        ResponseField.forEnum("firstAppearsIn", "firstAppearsIn", null, false, null)
                    )

            operator fun invoke(reader: ResponseReader): Friends {
                val __typename = reader.readString(RESPONSE_FIELDS[0])
                val firstAppearsIn = Episode.safeValueOf(reader.readString(RESPONSE_FIELDS[1]))
                return Friends(
                    __typename = __typename,
                    firstAppearsIn = firstAppearsIn
                )
            }
        }
    }

    /**
     * @param name What this human calls themselves@param homePlanet The home planet of the human, or null if unknown@param friends This human's friends, or an empty list if they have none */
    data class AsHuman1(
        val __typename: String,
        val name: String,
        val homePlanet: String?,
        val friends: List<Friends>?
    ) {
        fun marshaller(): ResponseFieldMarshaller = ResponseFieldMarshaller {
            it.writeString(RESPONSE_FIELDS[0], __typename)
            it.writeString(RESPONSE_FIELDS[1], name)
            it.writeString(RESPONSE_FIELDS[2], homePlanet)
            it.writeList(RESPONSE_FIELDS[3], friends) { value, listItemWriter ->
                        @Suppress("NAME_SHADOWING")
                        value?.forEach { value ->
                            listItemWriter.writeObject(value?.marshaller())}
                    }

        }

        companion object {
            private val RESPONSE_FIELDS: Array<ResponseField> = arrayOf(
                        ResponseField.forString("__typename", "__typename", null, false, null),
                        ResponseField.forString("name", "name", null, false, null),
                        ResponseField.forString("homePlanet", "homePlanet", null, true, null),
                        ResponseField.forList("friends", "friends", null, true, null)
                    )

            operator fun invoke(reader: ResponseReader): AsHuman1 {
                val __typename = reader.readString(RESPONSE_FIELDS[0])
                val name = reader.readString(RESPONSE_FIELDS[1])
                val homePlanet = reader.readString(RESPONSE_FIELDS[2])
                val friends = reader.readList<Friends>(RESPONSE_FIELDS[3]) {
                    it.readObject<Friends> {
                        Friends(it)
                    }

                }

                return AsHuman1(
                    __typename = __typename,
                    name = name,
                    homePlanet = homePlanet,
                    friends = friends
                )
            }
        }
    }

    /**
     * @param id The ID of the character@param deprecated Test deprecated field */
    data class Friends1(
        val __typename: String,
        val id: String,
        val deprecated: String
    ) {
        fun marshaller(): ResponseFieldMarshaller = ResponseFieldMarshaller {
            it.writeString(RESPONSE_FIELDS[0], __typename)
            it.writeCustom(RESPONSE_FIELDS[1] as ResponseField.CustomTypeField, id)
            it.writeString(RESPONSE_FIELDS[2], deprecated)
        }

        companion object {
            private val RESPONSE_FIELDS: Array<ResponseField> = arrayOf(
                        ResponseField.forString("__typename", "__typename", null, false, null),
                        ResponseField.forCustomType("id", "id", null, false, CustomType.ID, null),
                        ResponseField.forString("deprecated", "deprecated", null, false, null)
                    )

            operator fun invoke(reader: ResponseReader): Friends1 {
                val __typename = reader.readString(RESPONSE_FIELDS[0])
                val id = reader.readCustomType<String>(RESPONSE_FIELDS[1] as ResponseField.CustomTypeField)
                val deprecated = reader.readString(RESPONSE_FIELDS[2])
                return Friends1(
                    __typename = __typename,
                    id = id,
                    deprecated = deprecated
                )
            }
        }
    }

    /**
     * @param name What others call this droid@param primaryFunction This droid's primary function@param friends This droid's friends, or an empty list if they have none */
    data class AsDroid(
        val __typename: String,
        val name: String,
        val primaryFunction: String?,
        val friends: List<Friends1>?
    ) {
        fun marshaller(): ResponseFieldMarshaller = ResponseFieldMarshaller {
            it.writeString(RESPONSE_FIELDS[0], __typename)
            it.writeString(RESPONSE_FIELDS[1], name)
            it.writeString(RESPONSE_FIELDS[2], primaryFunction)
            it.writeList(RESPONSE_FIELDS[3], friends) { value, listItemWriter ->
                        @Suppress("NAME_SHADOWING")
                        value?.forEach { value ->
                            listItemWriter.writeObject(value?.marshaller())}
                    }

        }

        companion object {
            private val RESPONSE_FIELDS: Array<ResponseField> = arrayOf(
                        ResponseField.forString("__typename", "__typename", null, false, null),
                        ResponseField.forString("name", "name", null, false, null),
                        ResponseField.forString("primaryFunction", "primaryFunction", null, true, null),
                        ResponseField.forList("friends", "friends", null, true, null)
                    )

            operator fun invoke(reader: ResponseReader): AsDroid {
                val __typename = reader.readString(RESPONSE_FIELDS[0])
                val name = reader.readString(RESPONSE_FIELDS[1])
                val primaryFunction = reader.readString(RESPONSE_FIELDS[2])
                val friends = reader.readList<Friends1>(RESPONSE_FIELDS[3]) {
                    it.readObject<Friends1> {
                        Friends1(it)
                    }

                }

                return AsDroid(
                    __typename = __typename,
                    name = name,
                    primaryFunction = primaryFunction,
                    friends = friends
                )
            }
        }
    }

    /**
     * @param name The name of the character */
    data class Friends12(
        val __typename: String,
        val name: String,
        val asHuman1: AsHuman1?,
        val asDroid: AsDroid?
    ) {
        fun marshaller(): ResponseFieldMarshaller = ResponseFieldMarshaller {
            it.writeString(RESPONSE_FIELDS[0], __typename)
            it.writeString(RESPONSE_FIELDS[1], name)
            it.writeObject(RESPONSE_FIELDS[2], asHuman1?.marshaller())
            it.writeObject(RESPONSE_FIELDS[3], asDroid?.marshaller())
        }

        companion object {
            private val RESPONSE_FIELDS: Array<ResponseField> = arrayOf(
                        ResponseField.forString("__typename", "__typename", null, false, null),
                        ResponseField.forString("name", "name", null, false, null),
                        ResponseField.forInlineFragment("__typename", "__typename", listOf("Human")),
                        ResponseField.forInlineFragment("__typename", "__typename", listOf("Droid"))
                    )

            operator fun invoke(reader: ResponseReader): Friends12 {
                val __typename = reader.readString(RESPONSE_FIELDS[0])
                val name = reader.readString(RESPONSE_FIELDS[1])
                val asHuman1 = reader.readConditional(RESPONSE_FIELDS[2]) { conditionalType, reader ->
                    AsHuman1(reader)
                }

                val asDroid = reader.readConditional(RESPONSE_FIELDS[3]) { conditionalType, reader ->
                    AsDroid(reader)
                }

                return Friends12(
                    __typename = __typename,
                    name = name,
                    asHuman1 = asHuman1,
                    asDroid = asDroid
                )
            }
        }
    }

    /**
     * @param id The ID of the character@param name The name of the character@param friends The friends of the character, or an empty list if they have none */
    data class AsHuman(
        val __typename: String,
        val id: String,
        val name: String,
        val friends: List<Friends12>?
    ) {
        fun marshaller(): ResponseFieldMarshaller = ResponseFieldMarshaller {
            it.writeString(RESPONSE_FIELDS[0], __typename)
            it.writeCustom(RESPONSE_FIELDS[1] as ResponseField.CustomTypeField, id)
            it.writeString(RESPONSE_FIELDS[2], name)
            it.writeList(RESPONSE_FIELDS[3], friends) { value, listItemWriter ->
                        @Suppress("NAME_SHADOWING")
                        value?.forEach { value ->
                            listItemWriter.writeObject(value?.marshaller())}
                    }

        }

        companion object {
            private val RESPONSE_FIELDS: Array<ResponseField> = arrayOf(
                        ResponseField.forString("__typename", "__typename", null, false, null),
                        ResponseField.forCustomType("id", "id", null, false, CustomType.ID, null),
                        ResponseField.forString("name", "name", null, false, null),
                        ResponseField.forList("friends", "friends", null, true, null)
                    )

            operator fun invoke(reader: ResponseReader): AsHuman {
                val __typename = reader.readString(RESPONSE_FIELDS[0])
                val id = reader.readCustomType<String>(RESPONSE_FIELDS[1] as ResponseField.CustomTypeField)
                val name = reader.readString(RESPONSE_FIELDS[2])
                val friends = reader.readList<Friends12>(RESPONSE_FIELDS[3]) {
                    it.readObject<Friends12> {
                        Friends12(it)
                    }

                }

                return AsHuman(
                    __typename = __typename,
                    id = id,
                    name = name,
                    friends = friends
                )
            }
        }
    }

    /**
     * @param firstAppearsIn The movie this character first appears in */
    data class Friends123(
        val __typename: String,
        val firstAppearsIn: Episode
    ) {
        fun marshaller(): ResponseFieldMarshaller = ResponseFieldMarshaller {
            it.writeString(RESPONSE_FIELDS[0], __typename)
            it.writeString(RESPONSE_FIELDS[1], firstAppearsIn.rawValue)
        }

        companion object {
            private val RESPONSE_FIELDS: Array<ResponseField> = arrayOf(
                        ResponseField.forString("__typename", "__typename", null, false, null),
                        ResponseField.forEnum("firstAppearsIn", "firstAppearsIn", null, false, null)
                    )

            operator fun invoke(reader: ResponseReader): Friends123 {
                val __typename = reader.readString(RESPONSE_FIELDS[0])
                val firstAppearsIn = Episode.safeValueOf(reader.readString(RESPONSE_FIELDS[1]))
                return Friends123(
                    __typename = __typename,
                    firstAppearsIn = firstAppearsIn
                )
            }
        }
    }

    /**
     * @param name What this human calls themselves@param homePlanet The home planet of the human, or null if unknown@param friends This human's friends, or an empty list if they have none */
    data class AsHuman12(
        val __typename: String,
        val name: String,
        val homePlanet: String?,
        val friends: List<Friends123>?
    ) {
        fun marshaller(): ResponseFieldMarshaller = ResponseFieldMarshaller {
            it.writeString(RESPONSE_FIELDS[0], __typename)
            it.writeString(RESPONSE_FIELDS[1], name)
            it.writeString(RESPONSE_FIELDS[2], homePlanet)
            it.writeList(RESPONSE_FIELDS[3], friends) { value, listItemWriter ->
                        @Suppress("NAME_SHADOWING")
                        value?.forEach { value ->
                            listItemWriter.writeObject(value?.marshaller())}
                    }

        }

        companion object {
            private val RESPONSE_FIELDS: Array<ResponseField> = arrayOf(
                        ResponseField.forString("__typename", "__typename", null, false, null),
                        ResponseField.forString("name", "name", null, false, null),
                        ResponseField.forString("homePlanet", "homePlanet", null, true, null),
                        ResponseField.forList("friends", "friends", null, true, null)
                    )

            operator fun invoke(reader: ResponseReader): AsHuman12 {
                val __typename = reader.readString(RESPONSE_FIELDS[0])
                val name = reader.readString(RESPONSE_FIELDS[1])
                val homePlanet = reader.readString(RESPONSE_FIELDS[2])
                val friends = reader.readList<Friends123>(RESPONSE_FIELDS[3]) {
                    it.readObject<Friends123> {
                        Friends123(it)
                    }

                }

                return AsHuman12(
                    __typename = __typename,
                    name = name,
                    homePlanet = homePlanet,
                    friends = friends
                )
            }
        }
    }

    /**
     * @param id The ID of the character@param deprecated Test deprecated field */
    data class Friends1234(
        val __typename: String,
        val id: String,
        val deprecated: String
    ) {
        fun marshaller(): ResponseFieldMarshaller = ResponseFieldMarshaller {
            it.writeString(RESPONSE_FIELDS[0], __typename)
            it.writeCustom(RESPONSE_FIELDS[1] as ResponseField.CustomTypeField, id)
            it.writeString(RESPONSE_FIELDS[2], deprecated)
        }

        companion object {
            private val RESPONSE_FIELDS: Array<ResponseField> = arrayOf(
                        ResponseField.forString("__typename", "__typename", null, false, null),
                        ResponseField.forCustomType("id", "id", null, false, CustomType.ID, null),
                        ResponseField.forString("deprecated", "deprecated", null, false, null)
                    )

            operator fun invoke(reader: ResponseReader): Friends1234 {
                val __typename = reader.readString(RESPONSE_FIELDS[0])
                val id = reader.readCustomType<String>(RESPONSE_FIELDS[1] as ResponseField.CustomTypeField)
                val deprecated = reader.readString(RESPONSE_FIELDS[2])
                return Friends1234(
                    __typename = __typename,
                    id = id,
                    deprecated = deprecated
                )
            }
        }
    }

    /**
     * @param name What others call this droid@param primaryFunction This droid's primary function@param friends This droid's friends, or an empty list if they have none */
    data class AsDroid12(
        val __typename: String,
        val name: String,
        val primaryFunction: String?,
        val friends: List<Friends1234>?
    ) {
        fun marshaller(): ResponseFieldMarshaller = ResponseFieldMarshaller {
            it.writeString(RESPONSE_FIELDS[0], __typename)
            it.writeString(RESPONSE_FIELDS[1], name)
            it.writeString(RESPONSE_FIELDS[2], primaryFunction)
            it.writeList(RESPONSE_FIELDS[3], friends) { value, listItemWriter ->
                        @Suppress("NAME_SHADOWING")
                        value?.forEach { value ->
                            listItemWriter.writeObject(value?.marshaller())}
                    }

        }

        companion object {
            private val RESPONSE_FIELDS: Array<ResponseField> = arrayOf(
                        ResponseField.forString("__typename", "__typename", null, false, null),
                        ResponseField.forString("name", "name", null, false, null),
                        ResponseField.forString("primaryFunction", "primaryFunction", null, true, null),
                        ResponseField.forList("friends", "friends", null, true, null)
                    )

            operator fun invoke(reader: ResponseReader): AsDroid12 {
                val __typename = reader.readString(RESPONSE_FIELDS[0])
                val name = reader.readString(RESPONSE_FIELDS[1])
                val primaryFunction = reader.readString(RESPONSE_FIELDS[2])
                val friends = reader.readList<Friends1234>(RESPONSE_FIELDS[3]) {
                    it.readObject<Friends1234> {
                        Friends1234(it)
                    }

                }

                return AsDroid12(
                    __typename = __typename,
                    name = name,
                    primaryFunction = primaryFunction,
                    friends = friends
                )
            }
        }
    }

    /**
     * @param name The name of the character */
    data class Friends12345(
        val __typename: String,
        val name: String,
        val asHuman12: AsHuman12?,
        val asDroid12: AsDroid12?
    ) {
        fun marshaller(): ResponseFieldMarshaller = ResponseFieldMarshaller {
            it.writeString(RESPONSE_FIELDS[0], __typename)
            it.writeString(RESPONSE_FIELDS[1], name)
            it.writeObject(RESPONSE_FIELDS[2], asHuman12?.marshaller())
            it.writeObject(RESPONSE_FIELDS[3], asDroid12?.marshaller())
        }

        companion object {
            private val RESPONSE_FIELDS: Array<ResponseField> = arrayOf(
                        ResponseField.forString("__typename", "__typename", null, false, null),
                        ResponseField.forString("name", "name", null, false, null),
                        ResponseField.forInlineFragment("__typename", "__typename", listOf("Human")),
                        ResponseField.forInlineFragment("__typename", "__typename", listOf("Droid"))
                    )

            operator fun invoke(reader: ResponseReader): Friends12345 {
                val __typename = reader.readString(RESPONSE_FIELDS[0])
                val name = reader.readString(RESPONSE_FIELDS[1])
                val asHuman12 = reader.readConditional(RESPONSE_FIELDS[2]) { conditionalType, reader ->
                    AsHuman12(reader)
                }

                val asDroid12 = reader.readConditional(RESPONSE_FIELDS[3]) { conditionalType, reader ->
                    AsDroid12(reader)
                }

                return Friends12345(
                    __typename = __typename,
                    name = name,
                    asHuman12 = asHuman12,
                    asDroid12 = asDroid12
                )
            }
        }
    }

    /**
     * @param id The ID of the character@param name The name of the character@param friends The friends of the character, or an empty list if they have none */
    data class AsDroid1(
        val __typename: String,
        val id: String,
        val name: String,
        val friends: List<Friends12345>?
    ) {
        fun marshaller(): ResponseFieldMarshaller = ResponseFieldMarshaller {
            it.writeString(RESPONSE_FIELDS[0], __typename)
            it.writeCustom(RESPONSE_FIELDS[1] as ResponseField.CustomTypeField, id)
            it.writeString(RESPONSE_FIELDS[2], name)
            it.writeList(RESPONSE_FIELDS[3], friends) { value, listItemWriter ->
                        @Suppress("NAME_SHADOWING")
                        value?.forEach { value ->
                            listItemWriter.writeObject(value?.marshaller())}
                    }

        }

        companion object {
            private val RESPONSE_FIELDS: Array<ResponseField> = arrayOf(
                        ResponseField.forString("__typename", "__typename", null, false, null),
                        ResponseField.forCustomType("id", "id", null, false, CustomType.ID, null),
                        ResponseField.forString("name", "name", null, false, null),
                        ResponseField.forList("friends", "friends", null, true, null)
                    )

            operator fun invoke(reader: ResponseReader): AsDroid1 {
                val __typename = reader.readString(RESPONSE_FIELDS[0])
                val id = reader.readCustomType<String>(RESPONSE_FIELDS[1] as ResponseField.CustomTypeField)
                val name = reader.readString(RESPONSE_FIELDS[2])
                val friends = reader.readList<Friends12345>(RESPONSE_FIELDS[3]) {
                    it.readObject<Friends12345> {
                        Friends12345(it)
                    }

                }

                return AsDroid1(
                    __typename = __typename,
                    id = id,
                    name = name,
                    friends = friends
                )
            }
        }
    }

    /**
     * @param name The name of the starship */
    data class AsStarship(
        val __typename: String,
        val name: String
    ) {
        fun marshaller(): ResponseFieldMarshaller = ResponseFieldMarshaller {
            it.writeString(RESPONSE_FIELDS[0], __typename)
            it.writeString(RESPONSE_FIELDS[1], name)
        }

        companion object {
            private val RESPONSE_FIELDS: Array<ResponseField> = arrayOf(
                        ResponseField.forString("__typename", "__typename", null, false, null),
                        ResponseField.forString("name", "name", null, false, null)
                    )

            operator fun invoke(reader: ResponseReader): AsStarship {
                val __typename = reader.readString(RESPONSE_FIELDS[0])
                val name = reader.readString(RESPONSE_FIELDS[1])
                return AsStarship(
                    __typename = __typename,
                    name = name
                )
            }
        }
    }

    data class Search(
        val __typename: String,
        val asHuman: AsHuman?,
        val asDroid1: AsDroid1?,
        val asStarship: AsStarship?
    ) {
        fun marshaller(): ResponseFieldMarshaller = ResponseFieldMarshaller {
            it.writeString(RESPONSE_FIELDS[0], __typename)
            it.writeObject(RESPONSE_FIELDS[1], asHuman?.marshaller())
            it.writeObject(RESPONSE_FIELDS[2], asDroid1?.marshaller())
            it.writeObject(RESPONSE_FIELDS[3], asStarship?.marshaller())
        }

        companion object {
            private val RESPONSE_FIELDS: Array<ResponseField> = arrayOf(
                        ResponseField.forString("__typename", "__typename", null, false, null),
                        ResponseField.forInlineFragment("__typename", "__typename", listOf("Human")),
                        ResponseField.forInlineFragment("__typename", "__typename", listOf("Droid")),
                        ResponseField.forInlineFragment("__typename", "__typename", listOf("Starship"))
                    )

            operator fun invoke(reader: ResponseReader): Search {
                val __typename = reader.readString(RESPONSE_FIELDS[0])
                val asHuman = reader.readConditional(RESPONSE_FIELDS[1]) { conditionalType, reader ->
                    AsHuman(reader)
                }

                val asDroid1 = reader.readConditional(RESPONSE_FIELDS[2]) { conditionalType, reader ->
                    AsDroid1(reader)
                }

                val asStarship = reader.readConditional(RESPONSE_FIELDS[3]) { conditionalType, reader ->
                    AsStarship(reader)
                }

                return Search(
                    __typename = __typename,
                    asHuman = asHuman,
                    asDroid1 = asDroid1,
                    asStarship = asStarship
                )
            }
        }
    }

    data class Data(val search: List<Search>?) : Operation.Data {
        override fun marshaller(): ResponseFieldMarshaller = ResponseFieldMarshaller {
            it.writeList(RESPONSE_FIELDS[0], search) { value, listItemWriter ->
                        @Suppress("NAME_SHADOWING")
                        value?.forEach { value ->
                            listItemWriter.writeObject(value?.marshaller())}
                    }

        }

        companion object {
            private val RESPONSE_FIELDS: Array<ResponseField> = arrayOf(
                        ResponseField.forList("search", "search", mapOf<String, Any>(
                            "text" to "test"), true, null)
                    )

            operator fun invoke(reader: ResponseReader): Data {
                val search = reader.readList<Search>(RESPONSE_FIELDS[0]) {
                    it.readObject<Search> {
                        Search(it)
                    }

                }

                return Data(
                    search = search
                )
            }
        }
    }

    companion object {
        val OPERATION_DEFINITION: String = """
                |query TestQuery {
                |  search(text: "test") {
                |    __typename
                |    ... on Character {
                |      id
                |      name
                |      friends {
                |        __typename
                |        ... on Character {
                |          name
                |        }
                |        ... on Human {
                |          homePlanet
                |          friends {
                |            __typename
                |            ... on Character {
                |              firstAppearsIn
                |            }
                |          }
                |        }
                |        ... on Droid {
                |          primaryFunction
                |          friends {
                |            __typename
                |            id
                |            deprecated
                |          }
                |        }
                |      }
                |    }
                |    ... on Starship {
                |      name
                |    }
                |  }
                |}
                """.trimMargin()

        const val OPERATION_ID: String =
                "1c0af4394c45ee39ec1e3eba06044a9834637fdef3dcfde107b2c801a8b27fa9"

        val QUERY_DOCUMENT: String = """
                |query TestQuery {
                |  search(text: "test") {
                |    __typename
                |    ... on Character {
                |      id
                |      name
                |      friends {
                |        __typename
                |        ... on Character {
                |          name
                |        }
                |        ... on Human {
                |          homePlanet
                |          friends {
                |            __typename
                |            ... on Character {
                |              firstAppearsIn
                |            }
                |          }
                |        }
                |        ... on Droid {
                |          primaryFunction
                |          friends {
                |            __typename
                |            id
                |            deprecated
                |          }
                |        }
                |      }
                |    }
                |    ... on Starship {
                |      name
                |    }
                |  }
                |}
                """.trimMargin()

        val OPERATION_NAME: OperationName = OperationName { "TestQuery" }
    }
}
