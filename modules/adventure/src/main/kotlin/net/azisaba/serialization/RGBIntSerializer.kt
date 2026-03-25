package net.azisaba.serialization

import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import net.kyori.adventure.util.RGBLike

object RGBIntSerializer : KSerializer<RGBLike> {
    override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor("RGBInt", PrimitiveKind.INT)

    override fun serialize(encoder: Encoder, value: RGBLike) {
        val red = value.red()
        val green = value.green()
        val blue = value.blue()

        require(red in 0..255 && green in 0..255 && blue in 0..255) {
            "RGB value must be between 0 and 255"
        }

        val packed = (red shl 16) or (green shl 8) or blue
        encoder.encodeInt(packed)
    }

    override fun deserialize(decoder: Decoder): RGBLike {
        val packed = decoder.decodeInt()

        require(packed in 0x000000..0xffffff) {
            "RGB packed int must be between 0x000000 and 0xffffff, got: $packed"
        }

        val red = (packed shr 16) and 0xff
        val green = (packed shr 8) and 0xff
        val blue = packed and 0xff

        return object : RGBLike {
            override fun red(): Int = red

            override fun green(): Int = green

            override fun blue(): Int = blue
        }
    }
}
