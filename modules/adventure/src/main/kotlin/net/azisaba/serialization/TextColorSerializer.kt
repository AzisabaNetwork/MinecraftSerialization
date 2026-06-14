package net.azisaba.serialization

import kotlinx.serialization.KSerializer
import kotlinx.serialization.SerializationException
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import net.kyori.adventure.text.format.TextColor

/**
 * A serializer implementation for [TextColor].
 *
 * The serialized form is a hexadecimal RGB string.
 *
 * ```json
 * "#123456"
 * ```
 *
 * @see TextColor
 */
object TextColorSerializer : KSerializer<TextColor> {
    override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor("TextColor", PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, value: TextColor) {
        encoder.encodeString(value.asHexString())
    }

    override fun deserialize(decoder: Decoder): TextColor {
        val hexString = decoder.decodeString()
        return TextColor.fromHexString(hexString) ?: throw SerializationException("Invalid hex string: $hexString")
    }
}
