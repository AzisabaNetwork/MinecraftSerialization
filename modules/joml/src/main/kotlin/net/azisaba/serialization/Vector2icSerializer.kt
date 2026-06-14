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
import org.joml.Vector2i
import org.joml.Vector2ic

/**
 * A serializer implementation for [Vector2ic].
 *
 * The serialized form is a two-element list of integer x and y components.
 *
 * ```json
 * [1, 2]
 * ```
 *
 * @see Vector2ic
 */
@OptIn(InternalSerializationApi::class)
object Vector2icSerializer : KSerializer<Vector2ic> {
    override val descriptor: SerialDescriptor = buildSerialDescriptor("Vector2ic", StructureKind.LIST)

    override fun serialize(encoder: Encoder, value: Vector2ic) {
        val composite = encoder.beginCollection(descriptor, 2)
        composite.encodeIntElement(descriptor, 0, value.x())
        composite.encodeIntElement(descriptor, 1, value.y())
        composite.endStructure(descriptor)
    }

    override fun deserialize(decoder: Decoder): Vector2ic {
        var x: Int? = null
        var y: Int? = null

        decoder.decodeStructure(descriptor) {
            while (true) {
                when (val index = decodeElementIndex(descriptor)) {
                    0 -> x = decodeIntElement(descriptor, 0)
                    1 -> y = decodeIntElement(descriptor, 1)
                    CompositeDecoder.DECODE_DONE -> break
                    else -> throw SerializationException("Vector2ic index must be between 0 and 1, got: $index")
                }
            }
        }

        return Vector2i(
            x ?: throw SerializationException("Vector2ic must contain x"),
            y ?: throw SerializationException("Vector2ic must contain y"),
        )
    }
}
