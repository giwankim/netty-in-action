package buildsrc.convention

plugins {
  id("java")
  id("com.diffplug.spotless")
}

java {
  toolchain {
    languageVersion.set(JavaLanguageVersion.of(21))
  }
}

configurations {
  compileOnly {
    extendsFrom(configurations.annotationProcessor.get())
  }
}

repositories {
  mavenCentral()
}

dependencies {
  implementation(platform("io.netty:netty-bom:4.1.119.Final"))
  implementation("io.netty:netty-all")
  compileOnly("org.projectlombok:lombok:1.18.36")
  annotationProcessor("org.projectlombok:lombok:1.18.36")
  testImplementation(platform("org.junit:junit-bom:5.12.0"))
  testImplementation("org.junit.jupiter:junit-jupiter")
  testImplementation("org.assertj:assertj-core:3.27.3")
}

tasks.withType<Test>().configureEach {
  useJUnitPlatform()
}

spotless {
  java {
    removeUnusedImports()
    googleJavaFormat().reorderImports(true)
    formatAnnotations()
  }
}
