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
import net.kyori.adventure.util.ARGBLike

@OptIn(InternalSerializationApi::class)
object ARGBArraySerializer : KSerializer<ARGBLike> {
    override val descriptor: SerialDescriptor = buildSerialDescriptor("ARGBArray", StructureKind.LIST)

    override fun serialize(encoder: Encoder, value: ARGBLike) {
        val red = value.red()
        val green = value.green()
        val blue = value.blue()
        val alpha = value.alpha()

        require(red in 0..255 && green in 0..255 && blue in 0..255 && alpha in 0..255) {
            "ARGB value must be between 0 and 255"
        }

        val composite = encoder.beginCollection(descriptor, 4)
        composite.encodeIntElement(descriptor, 0, red)
        composite.encodeIntElement(descriptor, 1, green)
        composite.encodeIntElement(descriptor, 2, blue)
        composite.encodeIntElement(descriptor, 3, alpha)
        composite.endStructure(descriptor)
    }

    override fun deserialize(decoder: Decoder): ARGBLike {
        var red: Int? = null
        var green: Int? = null
        var blue: Int? = null
        var alpha: Int? = null

        decoder.decodeStructure(descriptor) {
            while (true) {
                when (val index = decodeElementIndex(descriptor)) {
                    0 -> red = decodeIntElement(descriptor, 0)
                    1 -> green = decodeIntElement(descriptor, 1)
                    2 -> blue = decodeIntElement(descriptor, 2)
                    3 -> alpha = decodeIntElement(descriptor, 3)
                    CompositeDecoder.DECODE_DONE -> break
                    else -> throw SerializationException("ARGBArray index must be between 0 and 3, got: $index")
                }
            }
        }

        val r = red ?: throw SerializationException("ARGBArray must contain red")
        val g = green ?: throw SerializationException("ARGBArray must contain green")
        val b = blue ?: throw SerializationException("ARGBArray must contain blue")
        val a = alpha ?: throw SerializationException("ARGBArray must contain alpha")

        require(r in 0..255 && g in 0..255 && b in 0..255 && a in 0..255) {
            "ARGB value must be between 0 and 255"
        }

        return object : ARGBLike {
            override fun red(): Int = r

            override fun green(): Int = g

            override fun blue(): Int = b

            override fun alpha(): Int = a
        }
    }
}
