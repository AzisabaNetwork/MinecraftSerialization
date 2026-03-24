package net.azisaba.serialization

import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import net.kyori.adventure.key.Key

object KeySerializer : KSerializer<Key> {
    override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor("Key", PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, value: Key) {
        val string = value.toString()
        encoder.encodeString(string)
    }

    override fun deserialize(decoder: Decoder): Key {
        val string = decoder.decodeString()
        return Key.key(string)
    }
}
