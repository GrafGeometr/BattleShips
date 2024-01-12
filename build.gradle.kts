import com.saveourtool.diktat.plugin.gradle.DiktatGradlePlugin

plugins {
    kotlin("jvm") version "1.9.21"
    id("com.saveourtool.diktat") version "2.0.0"
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.8.0-RC2")
    testImplementation("org.jetbrains.kotlin:kotlin-test")
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(21)
}

allprojects {
    apply<DiktatGradlePlugin>()
}