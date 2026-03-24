package net.azisaba.serialization

import kotlinx.serialization.InternalSerializationApi
import kotlinx.serialization.KSerializer
import kotlinx.serialization.SerializationException
import kotlinx.serialization.descriptors.StructureKind
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.descriptors.buildSerialDescriptor
import kotlinx.serialization.encoding.CompositeDecoder
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.encoding.decodeStructure
import org.joml.Vector4d
import org.joml.Vector4dc

@OptIn(InternalSerializationApi::class)
object Vector4dcSerializer : KSerializer<Vector4dc> {
    override val descriptor: SerialDescriptor = buildSerialDescriptor("Vector4dc", StructureKind.LIST)

    override fun serialize(encoder: Encoder, value: Vector4dc) {
        val composite = encoder.beginCollection(descriptor, 4)
        composite.encodeDoubleElement(descriptor, 0, value.x())
        composite.encodeDoubleElement(descriptor, 1, value.y())
        composite.encodeDoubleElement(descriptor, 2, value.z())
        composite.encodeDoubleElement(descriptor, 3, value.w())
        composite.endStructure(descriptor)
    }

    override fun deserialize(decoder: Decoder): Vector4dc {
        var x: Double? = null
        var y: Double? = null
        var z: Double? = null
        var w: Double? = null

        decoder.decodeStructure(descriptor) {
            while (true) {
                when (val index = decodeElementIndex(descriptor)) {
                    0 -> x = decodeDoubleElement(descriptor, 0)
                    1 -> y = decodeDoubleElement(descriptor, 1)
                    2 -> z = decodeDoubleElement(descriptor, 2)
                    3 -> w = decodeDoubleElement(descriptor, 3)
                    CompositeDecoder.DECODE_DONE -> break
                    else -> throw SerializationException("Vector4dc index must be between 0 and 3, got: $index")
                }
            }
        }

        return Vector4d(
            x ?: throw SerializationException("Vector4dc must contain x"),
            y ?: throw SerializationException("Vector4dc must contain y"),
            z ?: throw SerializationException("Vector4dc must contain z"),
            w ?: throw SerializationException("Vector4dc must contain w"),
        )
    }
}
