package com.apollographql.apollo.gradle.test

import com.apollographql.apollo.gradle.internal.child
import com.apollographql.apollo.gradle.util.TestUtils
import com.apollographql.apollo.gradle.util.TestUtils.withProject
import com.apollographql.apollo.gradle.util.generatedChild
import org.gradle.testkit.runner.TaskOutcome
import org.hamcrest.CoreMatchers
import org.junit.Assert
import org.junit.Assert.assertThat
import org.junit.Assert.assertTrue
import org.junit.Test
import java.io.File

class KotlinCodegenTests {
  @Test
  fun `generates and compiles kotlin`() {
    val apolloConfiguration = """
      apollo {
        generateKotlinModels = true
      }
    """.trimIndent()
    withProject(usesKotlinDsl = false,
        apolloConfiguration = apolloConfiguration,
        plugins = listOf(TestUtils.javaPlugin, TestUtils.kotlinJvmPlugin, TestUtils.apolloPlugin)) { dir ->

      val source = TestUtils.fixturesDirectory()
      source.child("kotlin").copyRecursively(dir.child("src", "main", "kotlin"))

      TestUtils.executeTask("build", dir)
      assertTrue(File(dir, "build/classes/kotlin/main/com/example/DroidDetailsQuery.class").isFile)
      assertTrue(dir.generatedChild("main/service/com/example/DroidDetailsQuery.kt").isFile)
      assertTrue(dir.generatedChild("main/service/com/example/type/CustomType.kt").isFile)
      assertTrue(dir.generatedChild("main/service/com/example/fragment/SpeciesInformation.kt").isFile)
    }
  }


  @Test
  fun `when generateAsInternal set to true - generated models are internal`() {
    val apolloConfiguration = """
      apollo {
        generateAsInternal = true
      }
    """.trimIndent()
    withProject(usesKotlinDsl = false,
        apolloConfiguration = apolloConfiguration,
        plugins = listOf(TestUtils.javaPlugin, TestUtils.kotlinJvmPlugin, TestUtils.apolloPlugin)) { dir ->

      val source = TestUtils.fixturesDirectory()
      source.child("kotlin").copyRecursively(dir.child("src", "main", "kotlin"))

      TestUtils.executeTask("build", dir)
      assertTrue(File(dir, "build/classes/kotlin/main/com/example/DroidDetailsQuery.class").isFile)

      assertTrue(dir.generatedChild("main/service/com/example/DroidDetailsQuery.kt").isFile)
      assertThat(dir.generatedChild("main/service/com/example/DroidDetailsQuery.kt").readText(), CoreMatchers.containsString("internal class"))

      assertTrue(dir.generatedChild("main/service/com/example/type/CustomType.kt").isFile)
      assertThat(dir.generatedChild("main/service/com/example/type/CustomType.kt").readText(), CoreMatchers.containsString("internal class"))

      assertTrue(dir.generatedChild("main/service/com/example/fragment/SpeciesInformation.kt").isFile)
      assertThat(dir.generatedChild("main/service/com/example/fragment/SpeciesInformation.kt").readText(), CoreMatchers.containsString("internal data class"))
    }
  }
}
