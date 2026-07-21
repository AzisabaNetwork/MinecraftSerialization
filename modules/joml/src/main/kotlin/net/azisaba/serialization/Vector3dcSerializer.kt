package net.azisaba.serialization

import kotlinx.serialization.InternalSerializationApi
import kotlinx.serialization.KSerializer
import kotlinx.serialization.SerializationException
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.descriptors.StructureKind
import kotlinx.serialization.descriptors.buildSerialDescriptor
import kotlinx.serialization.encoding.*
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
object Vector3dcSerializer : KSerializer<Vector3dc> {
    @OptIn(InternalSerializationApi::class)
    override val descriptor: SerialDescriptor = buildSerialDescriptor("Vector3dc", StructureKind.LIST)

    override fun serialize(encoder: Encoder, value: Vector3dc) {
        encoder.encodeCollection(descriptor, 3) {
            encodeDoubleElement(descriptor, 0, value.x())
            encodeDoubleElement(descriptor, 1, value.y())
            encodeDoubleElement(descriptor, 2, value.z())
        }
    }

    override fun deserialize(decoder: Decoder): Vector3dc {
        return decoder.decodeStructure(descriptor) {
            var x: Double? = null
            var y: Double? = null
            var z: Double? = null

            while (true) {
                when (val index = decodeElementIndex(descriptor)) {
                    0 -> x = decodeDoubleElement(descriptor, index)
                    1 -> y = decodeDoubleElement(descriptor, index)
                    2 -> z = decodeDoubleElement(descriptor, index)
                    CompositeDecoder.DECODE_DONE -> break
                    else -> throw SerializationException("Unexpected index: $index")
                }
            }

            Vector3d(
                x ?: throw SerializationException("Vector3dc must contain x"),
                y ?: throw SerializationException("Vector3dc must contain y"),
                z ?: throw SerializationException("Vector3dc must contain z"),
            )
        }
    }
}
