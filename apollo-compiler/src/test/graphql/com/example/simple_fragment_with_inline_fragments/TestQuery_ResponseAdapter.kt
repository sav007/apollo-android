// AUTO-GENERATED FILE. DO NOT MODIFY.
//
// This class was automatically generated by Apollo GraphQL plugin from the GraphQL queries it found.
// It should not be modified by hand.
//
package com.example.simple_fragment_with_inline_fragments

import com.apollographql.apollo.api.ResponseField
import com.apollographql.apollo.api.internal.ResponseAdapter
import com.apollographql.apollo.api.internal.ResponseReader
import com.apollographql.apollo.api.internal.ResponseWriter
import kotlin.Array
import kotlin.Double
import kotlin.String
import kotlin.Suppress
import kotlin.collections.List

@Suppress("NAME_SHADOWING", "UNUSED_ANONYMOUS_PARAMETER", "LocalVariableName",
    "RemoveExplicitTypeArguments", "NestedLambdaShadowedImplicitParameter", "PropertyName",
    "RemoveRedundantQualifierName")
object TestQuery_ResponseAdapter : ResponseAdapter<TestQuery.Data> {
  private val RESPONSE_FIELDS: Array<ResponseField> = arrayOf(
    ResponseField.forObject("hero", "hero", null, true, null)
  )

  override fun fromResponse(reader: ResponseReader, __typename: String?): TestQuery.Data {
    return reader.run {
      var hero: TestQuery.Hero? = null
      while(true) {
        when (selectField(RESPONSE_FIELDS)) {
          0 -> hero = readObject<TestQuery.Hero>(RESPONSE_FIELDS[0]) { reader ->
            TestQuery_ResponseAdapter.Hero_ResponseAdapter.fromResponse(reader)
          }
          else -> break
        }
      }
      TestQuery.Data(
        hero = hero
      )
    }
  }

  override fun toResponse(writer: ResponseWriter, value: TestQuery.Data) {
    if(value.hero == null) {
      writer.writeObject(RESPONSE_FIELDS[0], null)
    } else {
      writer.writeObject(RESPONSE_FIELDS[0]) { writer ->
        TestQuery_ResponseAdapter.Hero_ResponseAdapter.toResponse(writer, value.hero)
      }
    }
  }

  object HumanFriend_ResponseAdapter : ResponseAdapter<TestQuery.HumanFriend> {
    private val RESPONSE_FIELDS: Array<ResponseField> = arrayOf(
      ResponseField.forString("__typename", "__typename", null, false, null),
      ResponseField.forString("name", "name", null, false, null),
      ResponseField.forDouble("height", "height", null, true, null)
    )

    override fun fromResponse(reader: ResponseReader, __typename: String?): TestQuery.HumanFriend {
      return reader.run {
        var __typename: String? = __typename
        var name: String? = null
        var height: Double? = null
        while(true) {
          when (selectField(RESPONSE_FIELDS)) {
            0 -> __typename = readString(RESPONSE_FIELDS[0])
            1 -> name = readString(RESPONSE_FIELDS[1])
            2 -> height = readDouble(RESPONSE_FIELDS[2])
            else -> break
          }
        }
        TestQuery.HumanFriend(
          __typename = __typename!!,
          name = name!!,
          height = height
        )
      }
    }

    override fun toResponse(writer: ResponseWriter, value: TestQuery.HumanFriend) {
      writer.writeString(RESPONSE_FIELDS[0], value.__typename)
      writer.writeString(RESPONSE_FIELDS[1], value.name)
      writer.writeDouble(RESPONSE_FIELDS[2], value.height)
    }
  }

  object DroidFriend_ResponseAdapter : ResponseAdapter<TestQuery.DroidFriend> {
    private val RESPONSE_FIELDS: Array<ResponseField> = arrayOf(
      ResponseField.forString("__typename", "__typename", null, false, null),
      ResponseField.forString("name", "name", null, false, null),
      ResponseField.forString("primaryFunction", "primaryFunction", null, true, null)
    )

    override fun fromResponse(reader: ResponseReader, __typename: String?): TestQuery.DroidFriend {
      return reader.run {
        var __typename: String? = __typename
        var name: String? = null
        var primaryFunction: String? = null
        while(true) {
          when (selectField(RESPONSE_FIELDS)) {
            0 -> __typename = readString(RESPONSE_FIELDS[0])
            1 -> name = readString(RESPONSE_FIELDS[1])
            2 -> primaryFunction = readString(RESPONSE_FIELDS[2])
            else -> break
          }
        }
        TestQuery.DroidFriend(
          __typename = __typename!!,
          name = name!!,
          primaryFunction = primaryFunction
        )
      }
    }

    override fun toResponse(writer: ResponseWriter, value: TestQuery.DroidFriend) {
      writer.writeString(RESPONSE_FIELDS[0], value.__typename)
      writer.writeString(RESPONSE_FIELDS[1], value.name)
      writer.writeString(RESPONSE_FIELDS[2], value.primaryFunction)
    }
  }

  object OtherFriend_ResponseAdapter : ResponseAdapter<TestQuery.OtherFriend> {
    private val RESPONSE_FIELDS: Array<ResponseField> = arrayOf(
      ResponseField.forString("__typename", "__typename", null, false, null),
      ResponseField.forString("name", "name", null, false, null)
    )

    override fun fromResponse(reader: ResponseReader, __typename: String?): TestQuery.OtherFriend {
      return reader.run {
        var __typename: String? = __typename
        var name: String? = null
        while(true) {
          when (selectField(RESPONSE_FIELDS)) {
            0 -> __typename = readString(RESPONSE_FIELDS[0])
            1 -> name = readString(RESPONSE_FIELDS[1])
            else -> break
          }
        }
        TestQuery.OtherFriend(
          __typename = __typename!!,
          name = name!!
        )
      }
    }

    override fun toResponse(writer: ResponseWriter, value: TestQuery.OtherFriend) {
      writer.writeString(RESPONSE_FIELDS[0], value.__typename)
      writer.writeString(RESPONSE_FIELDS[1], value.name)
    }
  }

  object Friend_ResponseAdapter : ResponseAdapter<TestQuery.Friend> {
    private val RESPONSE_FIELDS: Array<ResponseField> = arrayOf(
      ResponseField.forString("__typename", "__typename", null, false, null),
      ResponseField.forString("name", "name", null, false, null)
    )

    override fun fromResponse(reader: ResponseReader, __typename: String?): TestQuery.Friend {
      val typename = __typename ?: reader.readString(RESPONSE_FIELDS[0])
      return when(typename) {
        "Human" -> TestQuery_ResponseAdapter.HumanFriend_ResponseAdapter.fromResponse(reader, typename)
        "Droid" -> TestQuery_ResponseAdapter.DroidFriend_ResponseAdapter.fromResponse(reader, typename)
        else -> TestQuery_ResponseAdapter.OtherFriend_ResponseAdapter.fromResponse(reader, typename)
      }
    }

    override fun toResponse(writer: ResponseWriter, value: TestQuery.Friend) {
      when(value) {
        is TestQuery.HumanFriend -> TestQuery_ResponseAdapter.HumanFriend_ResponseAdapter.toResponse(writer, value)
        is TestQuery.DroidFriend -> TestQuery_ResponseAdapter.DroidFriend_ResponseAdapter.toResponse(writer, value)
        is TestQuery.OtherFriend -> TestQuery_ResponseAdapter.OtherFriend_ResponseAdapter.toResponse(writer, value)
      }
    }
  }

  object Hero_ResponseAdapter : ResponseAdapter<TestQuery.Hero> {
    private val RESPONSE_FIELDS: Array<ResponseField> = arrayOf(
      ResponseField.forString("__typename", "__typename", null, false, null),
      ResponseField.forString("name", "name", null, false, null),
      ResponseField.forList("friends", "friends", null, true, null)
    )

    override fun fromResponse(reader: ResponseReader, __typename: String?): TestQuery.Hero {
      return reader.run {
        var __typename: String? = __typename
        var name: String? = null
        var friends: List<TestQuery.Friend?>? = null
        while(true) {
          when (selectField(RESPONSE_FIELDS)) {
            0 -> __typename = readString(RESPONSE_FIELDS[0])
            1 -> name = readString(RESPONSE_FIELDS[1])
            2 -> friends = readList<TestQuery.Friend>(RESPONSE_FIELDS[2]) { reader ->
              reader.readObject<TestQuery.Friend> { reader ->
                TestQuery_ResponseAdapter.Friend_ResponseAdapter.fromResponse(reader)
              }
            }
            else -> break
          }
        }
        TestQuery.Hero(
          __typename = __typename!!,
          name = name!!,
          friends = friends
        )
      }
    }

    override fun toResponse(writer: ResponseWriter, value: TestQuery.Hero) {
      writer.writeString(RESPONSE_FIELDS[0], value.__typename)
      writer.writeString(RESPONSE_FIELDS[1], value.name)
      writer.writeList(RESPONSE_FIELDS[2], value.friends) { values, listItemWriter ->
        values?.forEach { value ->
          if(value == null) {
            listItemWriter.writeObject(null)
          } else {
            listItemWriter.writeObject { writer ->
              TestQuery_ResponseAdapter.Friend_ResponseAdapter.toResponse(writer, value)
            }
          }
        }
      }
    }
  }
}
