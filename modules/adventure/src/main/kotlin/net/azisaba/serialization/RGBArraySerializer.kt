package net.azisaba.serialization

import kotlinx.serialization.InternalSerializationApi
import kotlinx.serialization.KSerializer
import kotlinx.serialization.SerializationException
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.descriptors.StructureKind
import kotlinx.serialization.descriptors.buildSerialDescriptor
import kotlinx.serialization.encoding.CompositeDecoder
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.encoding.decodeStructure
import net.kyori.adventure.text.format.TextColor
import net.kyori.adventure.util.RGBLike

@OptIn(InternalSerializationApi::class)
object RGBArraySerializer : KSerializer<RGBLike> {
    override val descriptor: SerialDescriptor = buildSerialDescriptor("RGBArray", StructureKind.LIST)

    override fun serialize(encoder: Encoder, value: RGBLike) {
        val red = value.red()
        val green = value.green()
        val blue = value.blue()

        require(red in 0..255 && green in 0..255 && blue in 0..255) {
            "RGB value must be between 0 and 255"
        }

        val composite = encoder.beginCollection(descriptor, 3)
        composite.encodeIntElement(descriptor, 0, red)
        composite.encodeIntElement(descriptor, 1, green)
        composite.encodeIntElement(descriptor, 2, blue)
        composite.endStructure(descriptor)
    }

    override fun deserialize(decoder: Decoder): RGBLike {
        var red: Int? = null
        var green: Int? = null
        var blue: Int? = null

        decoder.decodeStructure(descriptor) {
            while (true) {
                when (val index = decodeElementIndex(descriptor)) {
                    0 -> red = decodeIntElement(descriptor, 0)
                    1 -> green = decodeIntElement(descriptor, 1)
                    2 -> blue = decodeIntElement(descriptor, 2)
                    CompositeDecoder.DECODE_DONE -> break
                    else -> throw SerializationException("RGBArray index must be between 0 and 2, got: $index")
                }
            }
        }

        val r = red ?: throw SerializationException("RGBArray must contain red")
        val g = green ?: throw SerializationException("RGBArray must contain green")
        val b = blue ?: throw SerializationException("RGBArray must contain blue")

        require(r in 0..255 && g in 0..255 && b in 0..255) {
            "RGB value must be between 0 and 255"
        }

        return TextColor.color(r, g, b)
    }
}
