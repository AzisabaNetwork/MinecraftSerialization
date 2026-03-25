package net.azisaba.serialization

import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import net.kyori.adventure.text.format.ShadowColor
import net.kyori.adventure.util.ARGBLike

object ARGBIntSerializer : KSerializer<ARGBLike> {
    override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor("ARGBInt", PrimitiveKind.INT)

    override fun serialize(encoder: Encoder, value: ARGBLike) {
        val alpha = value.alpha()
        val red = value.red()
        val green = value.green()
        val blue = value.blue()

        require(alpha in 0..255 && red in 0..255 && green in 0..255 && blue in 0..255) {
            "ARGB value must be between 0 and 255"
        }

        val packed = (alpha shl 24) or (red shl 16) or (green shl 8) or blue
        encoder.encodeInt(packed)
    }

    override fun deserialize(decoder: Decoder): ARGBLike {
        val packed = decoder.decodeInt()
        return ShadowColor.shadowColor(packed)
    }
}
