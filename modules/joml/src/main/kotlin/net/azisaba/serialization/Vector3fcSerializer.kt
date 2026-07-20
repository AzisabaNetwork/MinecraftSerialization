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
import org.joml.Vector3f
import org.joml.Vector3fc

/**
 * A serializer implementation for [Vector3fc].
 *
 * The serialized form is a three-element list of single-precision x, y, and z components.
 *
 * ```json
 * [1.25, 2.5, 3.75]
 * ```
 *
 * @see Vector3fc
 */
@OptIn(InternalSerializationApi::class)
object Vector3fcSerializer : KSerializer<Vector3fc> {
    override val descriptor: SerialDescriptor = buildSerialDescriptor("Vector3fc", StructureKind.LIST)

    override fun serialize(encoder: Encoder, value: Vector3fc) {
        val composite = encoder.beginCollection(descriptor, 3)
        composite.encodeFloatElement(descriptor, 0, value.x())
        composite.encodeFloatElement(descriptor, 1, value.y())
        composite.encodeFloatElement(descriptor, 2, value.z())
        composite.endStructure(descriptor)
    }

    override fun deserialize(decoder: Decoder): Vector3fc {
        return decoder.decodeStructure(descriptor) {
            var x: Float? = null
            var y: Float? = null
            var z: Float? = null

            while (true) {
                when (val index = decodeElementIndex(descriptor)) {
                    0 -> x = decodeFloatElement(descriptor, index)
                    1 -> y = decodeFloatElement(descriptor, index)
                    2 -> z = decodeFloatElement(descriptor, index)
                    CompositeDecoder.DECODE_DONE -> break
                    else -> throw SerializationException("Unexpected index: $index")
                }
            }

            Vector3f(
                x ?: throw SerializationException("Vector3fc must contain x"),
                y ?: throw SerializationException("Vector3fc must contain y"),
                z ?: throw SerializationException("Vector3fc must contain z"),
            )
        }
    }
}
