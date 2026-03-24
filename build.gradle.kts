import org.jetbrains.kotlin.gradle.dsl.KotlinJvmProjectExtension

plugins {
    base
    alias(libs.plugins.kotlin.jvm) apply false
    alias(libs.plugins.kotlin.serialization) apply false
}

group = "net.azisaba.serialization"
version = "1.0-SNAPSHOT"

val kotlinx = libs.kotlinx

configure(subprojects.filter { it.childProjects.isEmpty() }) {
    group = rootProject.group
    version = rootProject.version

    apply(plugin = "java-library")
    apply(plugin = "org.jetbrains.kotlin.jvm")

    repositories {
        mavenCentral()
    }

    dependencies {
        add("compileOnly", kotlinx.serialization.core)
        add("testImplementation", kotlin("test"))
    }

    configure<KotlinJvmProjectExtension> {
        jvmToolchain(21)
    }

    configure<JavaPluginExtension> {
        toolchain.languageVersion.set(JavaLanguageVersion.of(11))
    }
}
