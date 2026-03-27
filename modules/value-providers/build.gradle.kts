plugins {
    alias(libs.plugins.kotlin.serialization)
}

dependencies {
    testImplementation(libs.kotlinx.serialization.json)
}
