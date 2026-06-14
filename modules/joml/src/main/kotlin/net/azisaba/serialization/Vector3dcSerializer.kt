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
import org.joml.Vector3d
import org.joml.Vector3dc

/**
 * A serializer implementation for [Vector3dc].
 *
 * The serialized form is a three-element list of double-precision x, y, and z components.
 *
 * ```json
 * [1.25, 2.5, 3.75]
 * ```
 *
 * @see Vector3dc
 */
@OptIn(InternalSerializationApi::class)
object Vector3dcSerializer : KSerializer<Vector3dc> {
    override val descriptor: SerialDescriptor = buildSerialDescriptor("Vector3dc", StructureKind.LIST)

    override fun serialize(encoder: Encoder, value: Vector3dc) {
        val composite = encoder.beginCollection(descriptor, 3)
        composite.encodeDoubleElement(descriptor, 0, value.x())
        composite.encodeDoubleElement(descriptor, 1, value.y())
        composite.encodeDoubleElement(descriptor, 2, value.z())
        composite.endStructure(descriptor)
    }

    override fun deserialize(decoder: Decoder): Vector3dc {
        var x: Double? = null
        var y: Double? = null
        var z: Double? = null

        decoder.decodeStructure(descriptor) {
            while (true) {
                when (val index = decodeElementIndex(descriptor)) {
                    0 -> x = decodeDoubleElement(descriptor, 0)
                    1 -> y = decodeDoubleElement(descriptor, 1)
                    2 -> z = decodeDoubleElement(descriptor, 2)
                    CompositeDecoder.DECODE_DONE -> break
                    else -> throw SerializationException("Vector3dc index must be between 0 and 2, got: $index")
                }
            }
        }

        return Vector3d(
            x ?: throw SerializationException("Vector3dc must contain x"),
            y ?: throw SerializationException("Vector3dc must contain y"),
            z ?: throw SerializationException("Vector3dc must contain z"),
        )
    }
}
