// AUTO-GENERATED FILE. DO NOT MODIFY.
//
// This class was automatically generated by Apollo GraphQL plugin from the GraphQL queries it found.
// It should not be modified by hand.
//
package com.example.named_fragment_inside_inline_fragment.fragment

import kotlin.String
import kotlin.Suppress

@Suppress("NAME_SHADOWING", "UNUSED_ANONYMOUS_PARAMETER", "LocalVariableName",
    "RemoveExplicitTypeArguments", "NestedLambdaShadowedImplicitParameter", "PropertyName",
    "RemoveRedundantQualifierName")
interface CharacterName {
  val __typename: String

  /**
   * The name of the character
   */
  val name: String

  companion object {
    val FRAGMENT_DEFINITION: String = """
        |fragment characterName on Character {
        |  __typename
        |  name
        |}
        """.trimMargin()
  }
}
