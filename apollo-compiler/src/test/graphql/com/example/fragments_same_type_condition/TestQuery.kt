// AUTO-GENERATED FILE. DO NOT MODIFY.
//
// This class was automatically generated by Apollo GraphQL plugin from the GraphQL queries it found.
// It should not be modified by hand.
//
package com.example.fragments_same_type_condition

import com.apollographql.apollo.api.Operation
import com.apollographql.apollo.api.OperationName
import com.apollographql.apollo.api.Query
import com.apollographql.apollo.api.internal.QueryDocumentMinifier
import com.apollographql.apollo.api.internal.ResponseFieldMapper
import com.apollographql.apollo.api.internal.ResponseFieldMarshaller
import com.example.fragments_same_type_condition.adapter.TestQuery_ResponseAdapter
import com.example.fragments_same_type_condition.fragment.DroidDetails1
import com.example.fragments_same_type_condition.fragment.DroidDetails2
import kotlin.String
import kotlin.Suppress

@Suppress("NAME_SHADOWING", "UNUSED_ANONYMOUS_PARAMETER", "LocalVariableName",
    "RemoveExplicitTypeArguments", "NestedLambdaShadowedImplicitParameter", "PropertyName",
    "RemoveRedundantQualifierName")
class TestQuery : Query<TestQuery.Data, Operation.Variables> {
  override fun operationId(): String = OPERATION_ID

  override fun queryDocument(): String = QUERY_DOCUMENT

  override fun variables(): Operation.Variables = Operation.EMPTY_VARIABLES

  override fun name(): OperationName = OPERATION_NAME

  override fun responseFieldMapper(): ResponseFieldMapper<Data> {
    return ResponseFieldMapper { reader ->
      TestQuery_ResponseAdapter.fromResponse(reader)
    }
  }

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

      fun marshaller(): ResponseFieldMarshaller

      interface Droid : Hero, DroidDetails1, DroidDetails2 {
        override val __typename: String

        /**
         * What others call this droid
         */
        override val name: String

        /**
         * This droid's primary function
         */
        override val primaryFunction: String?

        override fun marshaller(): ResponseFieldMarshaller
      }

      data class DroidHero(
        override val __typename: String,
        /**
         * What others call this droid
         */
        override val name: String,
        /**
         * This droid's primary function
         */
        override val primaryFunction: String?
      ) : Hero, Droid, DroidDetails1, DroidDetails2 {
        override fun marshaller(): ResponseFieldMarshaller {
          return ResponseFieldMarshaller { writer ->
            TestQuery_ResponseAdapter.Data.Hero.DroidHero.toResponse(writer, this)
          }
        }
      }

      data class OtherHero(
        override val __typename: String
      ) : Hero {
        override fun marshaller(): ResponseFieldMarshaller {
          return ResponseFieldMarshaller { writer ->
            TestQuery_ResponseAdapter.Data.Hero.OtherHero.toResponse(writer, this)
          }
        }
      }

      companion object {
        fun Hero.asDroid(): Droid? = this as? Droid

        fun Hero.droidDetails1(): DroidDetails1? = this as? DroidDetails1

        fun Hero.droidDetails2(): DroidDetails2? = this as? DroidDetails2
      }
    }
  }

  companion object {
    const val OPERATION_ID: String =
        "4146de3cd98689d76350979dfcd21d35c4be6bb8727b689e26706fdbbb091383"

    val QUERY_DOCUMENT: String = QueryDocumentMinifier.minify(
          """
          |query TestQuery {
          |  hero {
          |    __typename
          |    ...DroidDetails1
          |    ...DroidDetails2
          |  }
          |}
          |fragment DroidDetails1 on Droid {
          |  __typename
          |  name
          |}
          |fragment DroidDetails2 on Droid {
          |  __typename
          |  primaryFunction
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
