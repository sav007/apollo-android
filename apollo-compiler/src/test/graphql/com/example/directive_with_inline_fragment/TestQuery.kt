// AUTO-GENERATED FILE. DO NOT MODIFY.
//
// This class was automatically generated by Apollo GraphQL plugin from the GraphQL queries it found.
// It should not be modified by hand.
//
package com.example.directive_with_inline_fragment

import com.apollographql.apollo.api.CustomScalarAdapters
import com.apollographql.apollo.api.CustomScalarAdapters.Companion.DEFAULT
import com.apollographql.apollo.api.Operation
import com.apollographql.apollo.api.OperationName
import com.apollographql.apollo.api.Query
<<<<<<< HEAD
=======
import com.apollographql.apollo.api.Response
>>>>>>> dev-3.x
import com.apollographql.apollo.api.internal.InputFieldMarshaller
import com.apollographql.apollo.api.internal.QueryDocumentMinifier
import com.apollographql.apollo.api.internal.ResponseFieldMapper
import com.apollographql.apollo.api.internal.ResponseFieldMarshaller
import com.example.directive_with_inline_fragment.adapter.TestQuery_ResponseAdapter
import kotlin.Any
import kotlin.Boolean
import kotlin.String
import kotlin.Suppress
import kotlin.collections.Map
import kotlin.jvm.Transient

@Suppress("NAME_SHADOWING", "UNUSED_ANONYMOUS_PARAMETER", "LocalVariableName",
    "RemoveExplicitTypeArguments", "NestedLambdaShadowedImplicitParameter", "PropertyName",
    "RemoveRedundantQualifierName")
data class TestQuery(
  val withDetails: Boolean,
  val skipHumanDetails: Boolean
) : Query<TestQuery.Data, Operation.Variables> {
  @Transient
  private val variables: Operation.Variables = object : Operation.Variables() {
    override fun valueMap(): Map<String, Any?> = mutableMapOf<String, Any?>().apply {
      this["withDetails"] = this@TestQuery.withDetails
      this["skipHumanDetails"] = this@TestQuery.skipHumanDetails
    }

    override fun marshaller(): InputFieldMarshaller {
      return InputFieldMarshaller.invoke { writer ->
        writer.writeBoolean("withDetails", this@TestQuery.withDetails)
        writer.writeBoolean("skipHumanDetails", this@TestQuery.skipHumanDetails)
      }
    }
  }

  override fun operationId(): String = OPERATION_ID

  override fun queryDocument(): String = QUERY_DOCUMENT

  override fun variables(): Operation.Variables = variables

  override fun name(): OperationName = OPERATION_NAME

  override fun responseFieldMapper(): ResponseFieldMapper<Data> {
    return ResponseFieldMapper { reader ->
      TestQuery_ResponseAdapter.fromResponse(reader)
    }
  }

<<<<<<< HEAD
=======
  @Throws(IOException::class)
  override fun parse(source: BufferedSource, customScalarAdapters: CustomScalarAdapters):
      Response<Data> {
    return SimpleOperationResponseParser.parse(source, this, customScalarAdapters)
  }

  @Throws(IOException::class)
  override fun parse(byteString: ByteString, customScalarAdapters: CustomScalarAdapters):
      Response<Data> {
    return parse(Buffer().write(byteString), customScalarAdapters)
  }

  @Throws(IOException::class)
  override fun parse(source: BufferedSource): Response<Data> {
    return parse(source, DEFAULT)
  }

  @Throws(IOException::class)
  override fun parse(byteString: ByteString): Response<Data> {
    return parse(byteString, DEFAULT)
  }

  override fun composeRequestBody(customScalarAdapters: CustomScalarAdapters): ByteString {
    return OperationRequestBodyComposer.compose(
      operation = this,
      autoPersistQueries = false,
      withQueryDocument = true,
      customScalarAdapters = customScalarAdapters
    )
  }

  override fun composeRequestBody(): ByteString = OperationRequestBodyComposer.compose(
    operation = this,
    autoPersistQueries = false,
    withQueryDocument = true,
    customScalarAdapters = DEFAULT
  )

  override fun composeRequestBody(
    autoPersistQueries: Boolean,
    withQueryDocument: Boolean,
    customScalarAdapters: CustomScalarAdapters
  ): ByteString = OperationRequestBodyComposer.compose(
    operation = this,
    autoPersistQueries = autoPersistQueries,
    withQueryDocument = withQueryDocument,
    customScalarAdapters = customScalarAdapters
  )

>>>>>>> dev-3.x
  /**
   * The query type, represents all of the entry points into our object graph
   */
  data class Data(
    val hero: Hero?
  ) : Operation.Data {
    override fun marshaller(): ResponseFieldMarshaller {
      return ResponseFieldMarshaller { writer ->
        TestQuery_ResponseAdapter.Data.toResponse(writer, this)
      }
    }

    /**
     * A character from the Star Wars universe
     */
    interface Hero {
      val __typename: String

      /**
       * The ID of the character
       */
      val id: String

      fun marshaller(): ResponseFieldMarshaller

      interface Human : Hero {
        override val __typename: String

        /**
         * The ID of the character
         */
        override val id: String

        /**
         * What this human calls themselves
         */
        val name: String

        /**
         * The home planet of the human, or null if unknown
         */
        val homePlanet: String?

        override fun marshaller(): ResponseFieldMarshaller
      }

      interface Droid : Hero {
        override val __typename: String

        /**
         * The ID of the character
         */
        override val id: String

        /**
         * What others call this droid
         */
        val name: String

        /**
         * This droid's primary function
         */
        val primaryFunction: String?

        override fun marshaller(): ResponseFieldMarshaller
      }

      interface Character : Hero {
        override val __typename: String

        /**
         * The ID of the character
         */
        override val id: String

        /**
         * The name of the character
         */
        val name: String

        override fun marshaller(): ResponseFieldMarshaller
      }

      data class HumanCharacterHero(
        override val __typename: String,
        /**
         * The ID of the character
         */
        override val id: String,
        /**
         * What this human calls themselves
         */
        override val name: String,
        /**
         * The home planet of the human, or null if unknown
         */
        override val homePlanet: String?
      ) : Hero, Human, Character {
        override fun marshaller(): ResponseFieldMarshaller {
          return ResponseFieldMarshaller { writer ->
            TestQuery_ResponseAdapter.Data.Hero.HumanCharacterHero.toResponse(writer, this)
          }
        }
      }

      data class DroidCharacterHero(
        override val __typename: String,
        /**
         * The ID of the character
         */
        override val id: String,
        /**
         * What others call this droid
         */
        override val name: String,
        /**
         * This droid's primary function
         */
        override val primaryFunction: String?
      ) : Hero, Droid, Character {
        override fun marshaller(): ResponseFieldMarshaller {
          return ResponseFieldMarshaller { writer ->
            TestQuery_ResponseAdapter.Data.Hero.DroidCharacterHero.toResponse(writer, this)
          }
        }
      }

      data class OtherHero(
        override val __typename: String,
        /**
         * The ID of the character
         */
        override val id: String
      ) : Hero {
        override fun marshaller(): ResponseFieldMarshaller {
          return ResponseFieldMarshaller { writer ->
            TestQuery_ResponseAdapter.Data.Hero.OtherHero.toResponse(writer, this)
          }
        }
      }

      companion object {
        fun Hero.asHuman(): Human? = this as? Human

        fun Hero.asDroid(): Droid? = this as? Droid

        fun Hero.asCharacter(): Character? = this as? Character
      }
    }
  }

  companion object {
    const val OPERATION_ID: String =
        "1fc50a1808d1ff72f74d821b563ee69df2fc04dd650e41d27d75d90d0413bd65"

    val QUERY_DOCUMENT: String = QueryDocumentMinifier.minify(
          """
          |query TestQuery(${'$'}withDetails: Boolean!, ${'$'}skipHumanDetails: Boolean!) {
          |  hero {
          |    __typename
          |    id
          |    ... on Human @include(if: ${'$'}withDetails) @skip(if: ${'$'}skipHumanDetails) {
          |      name
          |      homePlanet
          |    }
          |    ... on Droid @include(if: ${'$'}withDetails) {
          |      name
          |      primaryFunction
          |    }
          |    ... on Character @include(if: ${'$'}withDetails) {
          |      name
          |    }
          |  }
          |}
          """.trimMargin()
        )

    val OPERATION_NAME: OperationName = object : OperationName {
      override fun name(): String {
        return "TestQuery"
      }
    }
  }
}
