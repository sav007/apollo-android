// AUTO-GENERATED FILE. DO NOT MODIFY.
//
// This class was automatically generated by Apollo GraphQL plugin from the GraphQL queries it found.
// It should not be modified by hand.
//
package com.example.test_inline

import com.apollographql.apollo.api.Operation
import com.apollographql.apollo.api.OperationName
import com.apollographql.apollo.api.Query
import com.apollographql.apollo.api.Response
import com.apollographql.apollo.api.ScalarTypeAdapters
import com.apollographql.apollo.api.ScalarTypeAdapters.Companion.DEFAULT
import com.apollographql.apollo.api.internal.OperationRequestBodyComposer
import com.apollographql.apollo.api.internal.QueryDocumentMinifier
import com.apollographql.apollo.api.internal.ResponseFieldMapper
import com.apollographql.apollo.api.internal.ResponseFieldMarshaller
import com.apollographql.apollo.api.internal.SimpleOperationResponseParser
import com.apollographql.apollo.api.internal.Throws
import com.example.test_inline.adapter.GetPage_ResponseAdapter
import kotlin.Boolean
import kotlin.String
import kotlin.Suppress
import kotlin.collections.List
import okio.Buffer
import okio.BufferedSource
import okio.ByteString
import okio.IOException

@Suppress("NAME_SHADOWING", "UNUSED_ANONYMOUS_PARAMETER", "LocalVariableName",
    "RemoveExplicitTypeArguments", "NestedLambdaShadowedImplicitParameter", "PropertyName",
    "RemoveRedundantQualifierName")
class GetPage : Query<GetPage.Data, Operation.Variables> {
  override fun operationId(): String = OPERATION_ID

  override fun queryDocument(): String = QUERY_DOCUMENT

  override fun variables(): Operation.Variables = Operation.EMPTY_VARIABLES

  override fun name(): OperationName = OPERATION_NAME

  override fun responseFieldMapper(): ResponseFieldMapper<Data> {
    return ResponseFieldMapper { reader ->
      GetPage_ResponseAdapter.fromResponse(reader)
    }
  }

  @Throws(IOException::class)
  override fun parse(source: BufferedSource, scalarTypeAdapters: ScalarTypeAdapters):
      Response<Data> {
    return SimpleOperationResponseParser.parse(source, this, scalarTypeAdapters)
  }

  @Throws(IOException::class)
  override fun parse(byteString: ByteString, scalarTypeAdapters: ScalarTypeAdapters):
      Response<Data> {
    return parse(Buffer().write(byteString), scalarTypeAdapters)
  }

  @Throws(IOException::class)
  override fun parse(source: BufferedSource): Response<Data> {
    return parse(source, DEFAULT)
  }

  @Throws(IOException::class)
  override fun parse(byteString: ByteString): Response<Data> {
    return parse(byteString, DEFAULT)
  }

  override fun composeRequestBody(scalarTypeAdapters: ScalarTypeAdapters): ByteString {
    return OperationRequestBodyComposer.compose(
      operation = this,
      autoPersistQueries = false,
      withQueryDocument = true,
      scalarTypeAdapters = scalarTypeAdapters
    )
  }

  override fun composeRequestBody(): ByteString = OperationRequestBodyComposer.compose(
    operation = this,
    autoPersistQueries = false,
    withQueryDocument = true,
    scalarTypeAdapters = DEFAULT
  )

  override fun composeRequestBody(
    autoPersistQueries: Boolean,
    withQueryDocument: Boolean,
    scalarTypeAdapters: ScalarTypeAdapters
  ): ByteString = OperationRequestBodyComposer.compose(
    operation = this,
    autoPersistQueries = autoPersistQueries,
    withQueryDocument = withQueryDocument,
    scalarTypeAdapters = scalarTypeAdapters
  )

  data class Data(
    val collection: Collection
  ) : Operation.Data {
    override fun marshaller(): ResponseFieldMarshaller {
      return ResponseFieldMarshaller { writer ->
        GetPage_ResponseAdapter.Data.toResponse(writer, this)
      }
    }

    interface Collection {
      val __typename: String

      val items: List<Item>

      fun marshaller(): ResponseFieldMarshaller

      interface Item {
        val title: String

        fun marshaller(): ResponseFieldMarshaller
      }

      interface ParticularCollection : Collection {
        override val __typename: String

        override val items: List<Item>

        override fun marshaller(): ResponseFieldMarshaller

        interface Item : Collection.Item {
          override val title: String

          val __typename: String

          override fun marshaller(): ResponseFieldMarshaller

          interface ParticularItem : Item {
            override val __typename: String

            val image: String

            override val title: String

            override fun marshaller(): ResponseFieldMarshaller
          }

          companion object {
            fun Item.asParticularItem(): ParticularItem? = this as? ParticularItem
          }
        }
      }

      data class ParticularCollectionCollection(
        override val __typename: String,
        override val items: List<Item>
      ) : Collection, ParticularCollection {
        override fun marshaller(): ResponseFieldMarshaller {
          return ResponseFieldMarshaller { writer ->
            GetPage_ResponseAdapter.Data.Collection.ParticularCollectionCollection.toResponse(writer, this)
          }
        }

        interface Item : Collection.Item, ParticularCollection.Item {
          override val title: String

          override val __typename: String

          override fun marshaller(): ResponseFieldMarshaller

          interface ParticularItem : ParticularCollection.Item,
              ParticularCollection.Item.ParticularItem {
            override val __typename: String

            override val image: String

            override val title: String

            override fun marshaller(): ResponseFieldMarshaller
          }

          data class ParticularItemItem(
            override val title: String,
            override val __typename: String,
            override val image: String
          ) : ParticularCollection.Item, ParticularCollection.Item.ParticularItem, ParticularItem,
              Item {
            override fun marshaller(): ResponseFieldMarshaller {
              return ResponseFieldMarshaller { writer ->
                GetPage_ResponseAdapter.Data.Collection.ParticularCollectionCollection.Item.ParticularItemItem.toResponse(writer, this)
              }
            }
          }

          data class OtherItem(
            override val title: String,
            override val __typename: String
          ) : Collection.Item, ParticularCollection.Item, Item {
            override fun marshaller(): ResponseFieldMarshaller {
              return ResponseFieldMarshaller { writer ->
                GetPage_ResponseAdapter.Data.Collection.ParticularCollectionCollection.Item.OtherItem.toResponse(writer, this)
              }
            }
          }

          companion object {
            fun Item.asItems(): ParticularCollection.Item? = this as? ParticularCollection.Item

            fun Item.asParticularItem(): ParticularItem? = this as? ParticularItem
          }
        }
      }

      data class OtherCollection(
        override val __typename: String,
        override val items: List<Item>
      ) : Collection {
        override fun marshaller(): ResponseFieldMarshaller {
          return ResponseFieldMarshaller { writer ->
            GetPage_ResponseAdapter.Data.Collection.OtherCollection.toResponse(writer, this)
          }
        }

        data class Item(
          override val title: String
        ) : Collection.Item {
          override fun marshaller(): ResponseFieldMarshaller {
            return ResponseFieldMarshaller { writer ->
              GetPage_ResponseAdapter.Data.Collection.OtherCollection.Item.toResponse(writer, this)
            }
          }
        }
      }

      companion object {
        fun Collection.asParticularCollection(): ParticularCollection? = this as?
            ParticularCollection
      }
    }
  }

  companion object {
    const val OPERATION_ID: String =
        "09dd0a176a2233eccc3b2d3a76f25a1083460354f399f8b1aaf172c18cfc202b"

    val QUERY_DOCUMENT: String = QueryDocumentMinifier.minify(
          """
          |query GetPage {
          |  collection {
          |    __typename
          |    items {
          |      title
          |    }
          |    ... on ParticularCollection {
          |      items {
          |        __typename
          |        ... on ParticularItem {
          |          image
          |        }
          |      }
          |    }
          |  }
          |}
          """.trimMargin()
        )

    val OPERATION_NAME: OperationName = object : OperationName {
      override fun name(): String {
        return "GetPage"
      }
    }
  }
}
