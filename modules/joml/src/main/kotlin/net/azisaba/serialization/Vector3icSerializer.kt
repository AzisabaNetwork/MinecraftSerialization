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
import kotlinx.serialization.encoding.encodeCollection
import org.joml.Vector3i
import org.joml.Vector3ic

/**
 * A serializer implementation for [Vector3ic].
 *
 * The serialized form is a three-element list of integer x, y, and z components.
 *
 * ```json
 * [1, 2, 3]
 * ```
 *
 * @see Vector3ic
 */
object Vector3icSerializer : KSerializer<Vector3ic> {
    @OptIn(InternalSerializationApi::class)
    override val descriptor: SerialDescriptor = buildSerialDescriptor("Vector3ic", StructureKind.LIST)

    override fun serialize(encoder: Encoder, value: Vector3ic) {
        encoder.encodeCollection(descriptor, 3) {
            encodeIntElement(descriptor, 0, value.x())
            encodeIntElement(descriptor, 1, value.y())
            encodeIntElement(descriptor, 2, value.z())
        }
    }

    override fun deserialize(decoder: Decoder): Vector3ic {
        return decoder.decodeStructure(descriptor) {
            var x: Int? = null
            var y: Int? = null
            var z: Int? = null

            while (true) {
                when (val index = decodeElementIndex(descriptor)) {
                    0 -> x = decodeIntElement(descriptor, index)
                    1 -> y = decodeIntElement(descriptor, index)
                    2 -> z = decodeIntElement(descriptor, index)
                    CompositeDecoder.DECODE_DONE -> break
                    else -> throw SerializationException("Unexpected index: $index")
                }
            }

            Vector3i(
                x ?: throw SerializationException("Vector3ic must contain x"),
                y ?: throw SerializationException("Vector3ic must contain y"),
                z ?: throw SerializationException("Vector3ic must contain z"),
            )
        }
    }
}
