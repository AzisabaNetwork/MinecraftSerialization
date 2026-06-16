import net.azisaba.gradle.GenerateRegistrySerializers

repositories {
    maven {
        name = "papermc"
        url = uri("https://repo.papermc.io/repository/maven-public/")
    }
}

dependencies {
    api(project(":modules:adventure"))
    api(project(":modules:joml"))
    compileOnly(libs.paper.api)

    testImplementation(libs.kotlinx.serialization.json)
    testImplementation(libs.paper.api)
}

val generatedRegistrySerializersDir = layout.buildDirectory.dir("generated/sources/registrySerializers/kotlin")

val generateRegistrySerializers by tasks.registering(GenerateRegistrySerializers::class) {
    classpath.from(configurations.compileClasspath)
    outputDirectory.set(generatedRegistrySerializersDir)
}

kotlin {
    sourceSets.named("main") {
        kotlin.srcDir(generateRegistrySerializers)
    }
}
