package net.azisaba.serialization

import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import net.kyori.adventure.key.Key

/**
 * A serializer implementation for [Key].
 *
 * The serialized form is a namespaced string.
 *
 * ```json
 * "minecraft:stone"
 * ```
 *
 * @see Key
 */
object KeySerializer : KSerializer<Key> {
    override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor("Key", PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, value: Key) {
        encoder.encodeString(value.asString())
    }

    override fun deserialize(decoder: Decoder): Key {
        return Key.key(decoder.decodeString())
    }
}
