package net.azisaba.serialization

import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import net.kyori.adventure.text.format.ShadowColor
import net.kyori.adventure.text.format.TextColor
import net.kyori.adventure.util.ARGBLike

object ARGBHexSerializer : KSerializer<ARGBLike> {
    override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor("ARGBHex", PrimitiveKind.STRING)

    private val HEX_REGEX: Regex = Regex("[0-9a-fA-F]{8}")

    override fun serialize(encoder: Encoder, value: ARGBLike) {
        val alpha = value.alpha()
        val red = value.red()
        val green = value.green()
        val blue = value.blue()

        require(alpha in 0..255 && red in 0..255 && green in 0..255 && blue in 0..255) {
            "ARGB value must be between 0 and 255"
        }

        val hex = TextColor.HEX_PREFIX + "%02x%02x%02x%02x".format(red, green, blue, alpha)
        encoder.encodeString(hex)
    }

    override fun deserialize(decoder: Decoder): ARGBLike {
        val string = decoder.decodeString()
        val hex = string.removePrefix(TextColor.HEX_PREFIX)

        require(HEX_REGEX.matches(hex)) {
            "Invalid ARGB HEX color: $string"
        }

        val value = hex.toLong(16)

        val red = ((value shr 24) and 0xff).toInt()
        val green = ((value shr 16) and 0xff).toInt()
        val blue = ((value shr 8) and 0xff).toInt()
        val alpha = (value and 0xff).toInt()

        return ShadowColor.shadowColor(red, green, blue, alpha)
    }
}
