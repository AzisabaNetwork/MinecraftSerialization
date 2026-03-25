package net.azisaba.serialization

import kotlinx.serialization.KSerializer
import kotlinx.serialization.SerializationException
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import net.kyori.adventure.text.format.TextColor
import net.kyori.adventure.util.RGBLike

object RGBLikeHexSerializer : KSerializer<RGBLike> {
    override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor("RGBLikeHex", PrimitiveKind.STRING)

    private val HEX_REGEX: Regex = Regex("[0-9a-fA-F]{6}")

    override fun serialize(encoder: Encoder, value: RGBLike) {
        val red = value.red()
        val green = value.green()
        val blue = value.blue()

        require(red in 0..255 && green in 0..255 && blue in 0..255) {
            "RGB value must be between 0 and 255"
        }

        val hex = TextColor.HEX_PREFIX + "%02x%02x%02x".format(red, green, blue)
        encoder.encodeString(hex)
    }

    override fun deserialize(decoder: Decoder): RGBLike {
        val string = decoder.decodeString()
        val hex = string.removePrefix(TextColor.HEX_PREFIX)

        val normalized = when (hex.length) {
            6 -> hex.lowercase()
            3 -> buildString {
                for (c in hex.lowercase()) {
                    append(c)
                    append(c)
                }
            }

            else -> throw SerializationException("HEX color must be 3 or 6 characters, got $string")
        }

        require(HEX_REGEX.matches(normalized)) {
            "Invalid HEX color: $string"
        }

        val value = normalized.toInt(16)

        val red = (value shr 16) and 0xff
        val green = (value shr 8) and 0xff
        val blue = value and 0xff

        return TextColor.color(red, green, blue)
    }
}
