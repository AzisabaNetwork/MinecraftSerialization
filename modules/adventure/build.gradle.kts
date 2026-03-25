dependencies {
    compileOnly(libs.kotlinx.serialization.json)
    compileOnly(libs.adventure.api)
    compileOnly(libs.adventure.text.minimessage)
    compileOnly(libs.adventure.text.serializers.gson)

    testImplementation(libs.kotlinx.serialization.json)
    testImplementation(libs.adventure.api)
    testImplementation(libs.adventure.text.minimessage)
    testImplementation(libs.adventure.text.serializers.gson)
}
