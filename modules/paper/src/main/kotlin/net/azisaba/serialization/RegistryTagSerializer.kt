package net.azisaba.serialization

import io.papermc.paper.registry.RegistryAccess
import io.papermc.paper.registry.RegistryKey
import io.papermc.paper.registry.tag.Tag
import kotlinx.serialization.KSerializer
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import org.bukkit.Keyed

abstract class RegistryTagSerializer<T : Keyed>(private val registryKey: RegistryKey<T>) : KSerializer<Tag<T>> {
    override fun serialize(encoder: Encoder, value: Tag<T>) {
        val string = value.tagKey().key().asString()
        encoder.encodeString(string)
    }

    override fun deserialize(decoder: Decoder): Tag<T> {
        val string = decoder.decodeString()
        val tagKey = registryKey.tagKey(string)
        return RegistryAccess.registryAccess()
            .getRegistry(registryKey)
            .getTag(tagKey)
    }
}
