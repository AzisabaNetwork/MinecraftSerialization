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
import org.joml.Vector4i
import org.joml.Vector4ic

/**
 * A serializer implementation for [Vector4ic].
 *
 * The serialized form is a four-element list of integer x, y, z, and w components.
 *
 * ```json
 * [1, 2, 3, 4]
 * ```
 *
 * @see Vector4ic
 */
@OptIn(InternalSerializationApi::class)
object Vector4icSerializer : KSerializer<Vector4ic> {
    override val descriptor: SerialDescriptor = buildSerialDescriptor("Vector4ic", StructureKind.LIST)

    override fun serialize(encoder: Encoder, value: Vector4ic) {
        val composite = encoder.beginCollection(descriptor, 4)
        composite.encodeIntElement(descriptor, 0, value.x())
        composite.encodeIntElement(descriptor, 1, value.y())
        composite.encodeIntElement(descriptor, 2, value.z())
        composite.encodeIntElement(descriptor, 3, value.w())
        composite.endStructure(descriptor)
    }

    override fun deserialize(decoder: Decoder): Vector4ic {
        return decoder.decodeStructure(descriptor) {
            var x: Int? = null
            var y: Int? = null
            var z: Int? = null
            var w: Int? = null

            while (true) {
                when (val index = decodeElementIndex(descriptor)) {
                    0 -> x = decodeIntElement(descriptor, index)
                    1 -> y = decodeIntElement(descriptor, index)
                    2 -> z = decodeIntElement(descriptor, index)
                    3 -> w = decodeIntElement(descriptor, index)
                    CompositeDecoder.DECODE_DONE -> break
                    else -> throw SerializationException("Unexpected index: $index")
                }
            }

            Vector4i(
                x ?: throw SerializationException("Vector4ic must contain x"),
                y ?: throw SerializationException("Vector4ic must contain y"),
                z ?: throw SerializationException("Vector4ic must contain z"),
                w ?: throw SerializationException("Vector4ic must contain w"),
            )
        }
    }
}
