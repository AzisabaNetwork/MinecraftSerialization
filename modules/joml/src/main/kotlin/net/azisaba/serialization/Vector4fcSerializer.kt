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
import org.joml.Vector4f
import org.joml.Vector4fc

@OptIn(InternalSerializationApi::class)
object Vector4fcSerializer : KSerializer<Vector4fc> {
    override val descriptor: SerialDescriptor = buildSerialDescriptor("Vector4fc", StructureKind.LIST)

    override fun serialize(encoder: Encoder, value: Vector4fc) {
        val composite = encoder.beginCollection(descriptor, 4)
        composite.encodeFloatElement(descriptor, 0, value.x())
        composite.encodeFloatElement(descriptor, 1, value.y())
        composite.encodeFloatElement(descriptor, 2, value.z())
        composite.encodeFloatElement(descriptor, 3, value.w())
        composite.endStructure(descriptor)
    }

    override fun deserialize(decoder: Decoder): Vector4fc {
        var x: Float? = null
        var y: Float? = null
        var z: Float? = null
        var w: Float? = null

        decoder.decodeStructure(descriptor) {
            while (true) {
                when (val index = decodeElementIndex(descriptor)) {
                    0 -> x = decodeFloatElement(descriptor, 0)
                    1 -> y = decodeFloatElement(descriptor, 1)
                    2 -> z = decodeFloatElement(descriptor, 2)
                    3 -> w = decodeFloatElement(descriptor, 3)
                    CompositeDecoder.DECODE_DONE -> break
                    else -> throw SerializationException("Vector4fc index must be between 0 and 3, got: $index")
                }
            }
        }

        return Vector4f(
            x ?: throw SerializationException("Vector4fc must contain x"),
            y ?: throw SerializationException("Vector4fc must contain y"),
            z ?: throw SerializationException("Vector4fc must contain z"),
            w ?: throw SerializationException("Vector4fc must contain w"),
        )
    }
}
