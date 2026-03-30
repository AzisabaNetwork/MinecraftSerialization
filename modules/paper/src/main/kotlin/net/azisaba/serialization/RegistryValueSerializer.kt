package net.azisaba.serialization

import io.papermc.paper.registry.RegistryAccess
import io.papermc.paper.registry.RegistryKey
import kotlinx.serialization.KSerializer
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import org.bukkit.Keyed

abstract class RegistryValueSerializer<T : Keyed>(private val registryKey: RegistryKey<T>) : KSerializer<T> {
    override fun serialize(encoder: Encoder, value: T) {
        val string = value.key().asString()
        encoder.encodeString(string)
    }

    override fun deserialize(decoder: Decoder): T {
        val string = decoder.decodeString()
        val typedKey = registryKey.typedKey(string)
        return RegistryAccess.registryAccess()
            .getRegistry(registryKey)
            .getOrThrow(typedKey)
    }
}
