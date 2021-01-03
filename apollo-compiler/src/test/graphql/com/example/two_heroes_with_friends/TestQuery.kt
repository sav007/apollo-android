// AUTO-GENERATED FILE. DO NOT MODIFY.
//
// This class was automatically generated by Apollo GraphQL plugin from the GraphQL queries it found.
// It should not be modified by hand.
//
package com.example.two_heroes_with_friends

import com.apollographql.apollo.api.Operation
import com.apollographql.apollo.api.OperationName
import com.apollographql.apollo.api.Query
import com.apollographql.apollo.api.internal.QueryDocumentMinifier
import com.apollographql.apollo.api.internal.ResponseFieldMapper
import com.apollographql.apollo.api.internal.ResponseFieldMarshaller
import com.example.two_heroes_with_friends.adapter.TestQuery_ResponseAdapter
import kotlin.Int
import kotlin.String
import kotlin.Suppress
import kotlin.collections.List

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
    val r2: R2?,
    val luke: Luke?
  ) : Operation.Data {
    override fun marshaller(): ResponseFieldMarshaller {
      return ResponseFieldMarshaller { writer ->
        TestQuery_ResponseAdapter.Data.toResponse(writer, this)
      }
    }

    /**
     * A character from the Star Wars universe
     */
    data class R2(
      /**
       * The name of the character
       */
      val name: String,
      /**
       * The friends of the character exposed as a connection with edges
       */
      val friendsConnection: FriendsConnection
    ) {
      fun marshaller(): ResponseFieldMarshaller {
        return ResponseFieldMarshaller { writer ->
          TestQuery_ResponseAdapter.Data.R2.toResponse(writer, this)
        }
      }

      /**
       * A connection object for a character's friends
       */
      data class FriendsConnection(
        /**
         * The total number of friends
         */
        val totalCount: Int?,
        /**
         * The edges for each of the character's friends.
         */
        val edges: List<Edge?>?
      ) {
        fun marshaller(): ResponseFieldMarshaller {
          return ResponseFieldMarshaller { writer ->
            TestQuery_ResponseAdapter.Data.R2.FriendsConnection.toResponse(writer, this)
          }
        }

        fun edgesFilterNotNull(): List<Edge>? = edges?.filterNotNull()

        /**
         * An edge object for a character's friends
         */
        data class Edge(
          /**
           * The character represented by this friendship edge
           */
          val node: Node?
        ) {
          fun marshaller(): ResponseFieldMarshaller {
            return ResponseFieldMarshaller { writer ->
              TestQuery_ResponseAdapter.Data.R2.FriendsConnection.Edge.toResponse(writer, this)
            }
          }

          /**
           * A character from the Star Wars universe
           */
          data class Node(
            /**
             * The name of the character
             */
            val name: String
          ) {
            fun marshaller(): ResponseFieldMarshaller {
              return ResponseFieldMarshaller { writer ->
                TestQuery_ResponseAdapter.Data.R2.FriendsConnection.Edge.Node.toResponse(writer, this)
              }
            }
          }
        }
      }
    }

    /**
     * A character from the Star Wars universe
     */
    data class Luke(
      /**
       * The ID of the character
       */
      val id: String,
      /**
       * The name of the character
       */
      val name: String,
      /**
       * The friends of the character exposed as a connection with edges
       */
      val friendsConnection: FriendsConnection
    ) {
      fun marshaller(): ResponseFieldMarshaller {
        return ResponseFieldMarshaller { writer ->
          TestQuery_ResponseAdapter.Data.Luke.toResponse(writer, this)
        }
      }

      /**
       * A connection object for a character's friends
       */
      data class FriendsConnection(
        /**
         * The total number of friends
         */
        val totalCount: Int?,
        /**
         * The edges for each of the character's friends.
         */
        val edges: List<Edge?>?
      ) {
        fun marshaller(): ResponseFieldMarshaller {
          return ResponseFieldMarshaller { writer ->
            TestQuery_ResponseAdapter.Data.Luke.FriendsConnection.toResponse(writer, this)
          }
        }

        fun edgesFilterNotNull(): List<Edge>? = edges?.filterNotNull()

        /**
         * An edge object for a character's friends
         */
        data class Edge(
          /**
           * The character represented by this friendship edge
           */
          val node: Node?
        ) {
          fun marshaller(): ResponseFieldMarshaller {
            return ResponseFieldMarshaller { writer ->
              TestQuery_ResponseAdapter.Data.Luke.FriendsConnection.Edge.toResponse(writer, this)
            }
          }

          /**
           * A character from the Star Wars universe
           */
          data class Node(
            /**
             * The name of the character
             */
            val name: String
          ) {
            fun marshaller(): ResponseFieldMarshaller {
              return ResponseFieldMarshaller { writer ->
                TestQuery_ResponseAdapter.Data.Luke.FriendsConnection.Edge.Node.toResponse(writer, this)
              }
            }
          }
        }
      }
    }
  }

  companion object {
    const val OPERATION_ID: String =
        "28b644b6fcd59e0677f51136035c5fc1deb6419f12b819bff2e6bf4c5f659916"

    val QUERY_DOCUMENT: String = QueryDocumentMinifier.minify(
          """
          |query TestQuery {
          |  r2: hero {
          |    name
          |    friendsConnection {
          |      totalCount
          |      edges {
          |        node {
          |          name
          |        }
          |      }
          |    }
          |  }
          |  luke: hero(episode: EMPIRE) {
          |    id
          |    name
          |    friendsConnection {
          |      totalCount
          |      edges {
          |        node {
          |          name
          |        }
          |      }
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
