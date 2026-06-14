package net.azisaba.serialization

import kotlinx.serialization.KSerializer
import kotlinx.serialization.SerializationException
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import net.kyori.adventure.text.format.ShadowColor

/**
 * A serializer implementation for [ShadowColor].
 *
 * The serialized form is a hexadecimal RGBA string.
 *
 * ```json
 * "#12345678"
 * ```
 *
 * @see ShadowColor
 */
object ShadowColorSerializer : KSerializer<ShadowColor> {
    override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor("ShadowColor", PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, value: ShadowColor) {
        encoder.encodeString(value.asHexString())
    }

    override fun deserialize(decoder: Decoder): ShadowColor {
        val hexString = decoder.decodeString()
        return ShadowColor.fromHexString(hexString) ?: throw SerializationException("Invalid hex string: $hexString")
    }
}
