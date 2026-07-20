package net.azisaba.serialization

import io.papermc.paper.registry.RegistryAccess
import io.papermc.paper.registry.RegistryKey
import io.papermc.paper.registry.set.RegistryKeySet
import io.papermc.paper.registry.set.RegistrySet
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
 * A serializer implementation for [RegistryKeySet].
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
 * @see RegistryKeySet
 * @see RegistryKey
 */
@ApiStatus.NonExtendable
abstract class RegistryKeySetSerializer<T : Keyed>(
    val registryKey: RegistryKey<T>,
) : KSerializer<RegistryKeySet<T>> {
    override val descriptor: SerialDescriptor = listSerializer.descriptor

    override fun serialize(encoder: Encoder, value: RegistryKeySet<T>) {
        encoder.encodeSerializableValue(listSerializer, value.values().map { it.key().asString() })
    }

    override fun deserialize(decoder: Decoder): RegistryKeySet<T> {
        val registry = RegistryAccess.registryAccess().getRegistry(registryKey)

        val list = decoder.decodeSerializableValue(listSerializer)
        val keys = buildSet {
            for (element in list) {
                if (element.startsWith(TAG_PREFIX)) {
                    val tagKey = registryKey.tagKey(element.substring(1))
                    addAll(registry.getTag(tagKey).values())
                } else {
                    add(registryKey.typedKey(element))
                }
            }
        }

        return RegistrySet.keySet(registryKey, keys)
    }
}
