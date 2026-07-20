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

/**
 * A serializer implementation for [Vector4dc].
 *
 * The serialized form is a four-element list of double-precision x, y, z, and w components.
 *
 * ```json
 * [1.25, 2.5, 3.75, 4.0]
 * ```
 *
 * @see Vector4dc
 */
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
        return decoder.decodeStructure(descriptor) {
            var x: Double? = null
            var y: Double? = null
            var z: Double? = null
            var w: Double? = null

            while (true) {
                when (val index = decodeElementIndex(descriptor)) {
                    0 -> x = decodeDoubleElement(descriptor, index)
                    1 -> y = decodeDoubleElement(descriptor, index)
                    2 -> z = decodeDoubleElement(descriptor, index)
                    3 -> w = decodeDoubleElement(descriptor, index)
                    CompositeDecoder.DECODE_DONE -> break
                    else -> throw SerializationException("Unexpected index: $index")
                }
            }

            Vector4d(
                x ?: throw SerializationException("Vector4dc must contain x"),
                y ?: throw SerializationException("Vector4dc must contain y"),
                z ?: throw SerializationException("Vector4dc must contain z"),
                w ?: throw SerializationException("Vector4dc must contain w"),
            )
        }
    }
}
