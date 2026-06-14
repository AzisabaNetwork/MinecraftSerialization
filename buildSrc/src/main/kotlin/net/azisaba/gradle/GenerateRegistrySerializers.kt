package net.azisaba.gradle

import org.gradle.api.DefaultTask
import org.gradle.api.file.ConfigurableFileCollection
import org.gradle.api.file.DirectoryProperty
import org.gradle.api.tasks.CacheableTask
import org.gradle.api.tasks.Classpath
import org.gradle.api.tasks.OutputDirectory
import org.gradle.api.tasks.TaskAction
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type
import java.lang.reflect.WildcardType
import java.net.URLClassLoader

private val REGISTRY_SERIALIZER_KINDS: List<RegistrySerializerKind> = listOf(
    RegistrySerializerKind(
        fileName = "RegistryEntrySerializers.generated.kt",
        objectSuffix = "Serializer",
        supertype = "RegistryEntrySerializer",
        serializedForm = "the key resolved using",
        jsonExample = listOf("\"minecraft:example\""),
        includesDescriptorName = true,
    ),
    RegistrySerializerKind(
        fileName = "RegistryKeySetSerializers.generated.kt",
        objectSuffix = "KeySetSerializer",
        supertype = "RegistryKeySetSerializer",
        serializedForm = "a list of keys for",
        jsonExample = listOf(
            "[",
            "  \"minecraft:first\",",
            "  \"#minecraft:second\"",
            "]",
        ),
        includesDescriptorName = false,
    ),
    RegistrySerializerKind(
        fileName = "RegistryValueSetSerializers.generated.kt",
        objectSuffix = "ValueSetSerializer",
        supertype = "RegistryValueSetSerializer",
        serializedForm = "a list of keys for",
        jsonExample = listOf(
            "[",
            "  \"minecraft:first\",",
            "  \"#minecraft:second\"",
            "]",
        ),
        includesDescriptorName = false,
    ),
)

private fun Type.toKotlinType(): String {
    return when (this) {
        is Class<*> -> canonicalName ?: name.replace('$', '.')
        is ParameterizedType -> {
            val typeArguments = actualTypeArguments.joinToString(", ") { it.toKotlinType() }
            "${rawType.toKotlinType()}<$typeArguments>"
        }

        is WildcardType -> "*"
        else -> typeName.replace('$', '.')
    }
}

private fun Type.rawClass(): Class<*> {
    return when (this) {
        is Class<*> -> this
        is ParameterizedType -> rawType.rawClass()
        else -> error("Unsupported registry value type: $typeName")
    }
}

private fun Class<*>.typeName(): String {
    return generateSequence(this) { it.enclosingClass }
        .toList()
        .asReversed()
        .joinToString("") { it.simpleName }
}

private fun Class<*>.serializerTypeName(): String {
    return if (canonicalName == "org.bukkit.Sound") "SoundType" else typeName()
}

@CacheableTask
abstract class GenerateRegistrySerializers : DefaultTask() {
    @get:Classpath
    abstract val classpath: ConfigurableFileCollection

    @get:OutputDirectory
    abstract val outputDirectory: DirectoryProperty

    @TaskAction
    fun generate() {
        val outputDirectory = outputDirectory.get().asFile
        outputDirectory.deleteRecursively()
        outputDirectory.mkdirs()

        URLClassLoader(
            classpath.files.map { it.toURI().toURL() }.toTypedArray(),
            ClassLoader.getPlatformClassLoader()
        ).use { classLoader ->
            val registryKeyClass = Class.forName("io.papermc.paper.registry.RegistryKey", false, classLoader)

            val registryFields = registryKeyClass.declaredFields
                .filter { it.type == registryKeyClass }
                .sortedBy { it.name }
                .map { field ->
                    val valueType = (field.genericType as ParameterizedType).actualTypeArguments.single()
                    RegistryField(field.name, valueType)
                }

            for (kind in REGISTRY_SERIALIZER_KINDS) {
                outputDirectory.resolve(kind.fileName).writeText(generateSource(registryFields, kind))
            }
        }
    }

    private fun generateSource(registryFields: List<RegistryField>, kind: RegistrySerializerKind): String {
        return buildString {
            appendLine("package net.azisaba.serialization")
            appendLine()
            appendLine("import io.papermc.paper.registry.RegistryKey")
            appendLine()

            for (field in registryFields) {
                val objectTypeName = field.rawValueClass.serializerTypeName()
                val descriptorName = field.rawValueClass.typeName()
                val kotlinType = field.valueType.toKotlinType()
                val kdocType = field.rawValueClass.canonicalName ?: field.rawValueClass.name.replace('$', '.')

                appendLine("/**")
                appendLine(" * A serializer implementation for [$kdocType].")
                appendLine(" *")
                appendLine(" * The serialized form is ${kind.serializedForm} [RegistryKey.${field.name}].")
                appendLine(" *")
                appendLine(" * ```json")
                kind.jsonExample.map { " * $it" }.forEach(::appendLine)
                appendLine(" * ```")
                appendLine(" *")
            appendLine(" * @see $kdocType")
            appendLine(" * @see RegistryKey.${field.name}")
            appendLine(" *")
            appendLine(" * NOTE: This serializer is automatically generated. Do not edit it manually.")
            appendLine(" */")
            appendLine("object $objectTypeName${kind.objectSuffix} :")
                append("    ${kind.supertype}<$kotlinType>(")
                if (kind.includesDescriptorName) {
                    appendLine()
                    appendLine("        \"$descriptorName\",")
                    appendLine("        RegistryKey.${field.name},")
                    append("    )")
                } else {
                    append("RegistryKey.${field.name})")
                }

            appendLine()
            appendLine()
        }
    }.trimEnd() + "\n"
    }
}

private data class RegistrySerializerKind(
    val fileName: String,
    val objectSuffix: String,
    val supertype: String,
    val serializedForm: String,
    val jsonExample: List<String>,
    val includesDescriptorName: Boolean,
)

private data class RegistryField(
    val name: String,
    val valueType: Type,
    val rawValueClass: Class<*> = valueType.rawClass(),
)
