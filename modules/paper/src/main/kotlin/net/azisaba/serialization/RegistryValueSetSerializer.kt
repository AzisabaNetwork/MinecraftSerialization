package net.azisaba.serialization

import io.papermc.paper.registry.RegistryAccess
import io.papermc.paper.registry.RegistryKey
import io.papermc.paper.registry.set.RegistrySet
import io.papermc.paper.registry.set.RegistryValueSet
import kotlinx.serialization.KSerializer
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.builtins.serializer
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import org.bukkit.Keyed

abstract class RegistryValueSetSerializer<T : Keyed>(
    private val registryKey: RegistryKey<T>,
) : KSerializer<RegistryValueSet<T>> {
    override fun serialize(encoder: Encoder, value: RegistryValueSet<T>) {
        val list = value.values().map { it.key().asString() }
        encoder.encodeSerializableValue(ListSerializer(String.serializer()), list)
    }

    override fun deserialize(decoder: Decoder): RegistryValueSet<T> {
        val list = decoder.decodeSerializableValue(ListSerializer(String.serializer()))

        val registry = RegistryAccess.registryAccess().getRegistry(registryKey)

        val values = mutableSetOf<T>()

        for (entry in list) {
            if (entry.startsWith(TAG_PREFIX)) {
                val tagKey = registryKey.tagKey(entry.substring(1))
                values += registry.getTagValues(tagKey)
            } else {
                val typedKey = registryKey.typedKey(entry)
                values += registry.getOrThrow(typedKey)
            }
        }

        return RegistrySet.valueSet(registryKey, values)
    }

    companion object {
        const val TAG_PREFIX: Char = '#'
    }
}
