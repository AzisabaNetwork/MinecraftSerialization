package net.azisaba.serialization

import io.papermc.paper.registry.RegistryAccess
import io.papermc.paper.registry.RegistryKey
import io.papermc.paper.registry.set.RegistrySet
import io.papermc.paper.registry.set.RegistryValueSet
import kotlinx.serialization.KSerializer
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.builtins.serializer
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import org.bukkit.Keyed
import org.jetbrains.annotations.ApiStatus

private const val TAG_PREFIX: Char = '#'

private val listSerializer: KSerializer<List<String>> = ListSerializer(String.serializer())

/**
 * A serializer implementation for [RegistryValueSet].
 *
 * The serialized form is a list of keys.
 *
 * ```json
 * [
 *   "minecraft:stone",
 *   "minecraft:dirt"
 * ]
 * ```
 *
 * @param T registry entry type
 * @see RegistryValueSet
 * @see RegistryKey
 */
@ApiStatus.Internal
abstract class RegistryValueSetSerializer<T : Keyed>(
    val registryKey: RegistryKey<T>,
) : KSerializer<RegistryValueSet<T>> {
    override val descriptor: SerialDescriptor = listSerializer.descriptor

    override fun serialize(encoder: Encoder, value: RegistryValueSet<T>) {
        encoder.encodeSerializableValue(listSerializer, value.values().map { it.key().asString() })
    }

    override fun deserialize(decoder: Decoder): RegistryValueSet<T> {
        val registry = RegistryAccess.registryAccess().getRegistry(registryKey)

        val list = decoder.decodeSerializableValue(listSerializer)
        val values = buildSet {
            for (element in list) {
                if (element.startsWith(TAG_PREFIX)) {
                    val tagKey = registryKey.tagKey(element.substring(1))
                    addAll(registry.getTagValues(tagKey))
                } else {
                    val typedKey = registryKey.typedKey(element)
                    add(registry.getOrThrow(typedKey))
                }
            }
        }

        return RegistrySet.valueSet(registryKey, values)
    }
}
