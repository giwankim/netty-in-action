package buildsrc.convention

plugins {
  kotlin("jvm")
  id("org.jlleitschuh.gradle.ktlint")
}

kotlin {
  jvmToolchain(21)
}

tasks.withType<Test>().configureEach {
  useJUnitPlatform()
}
