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
import org.joml.Vector2f
import org.joml.Vector2fc

/**
 * A serializer implementation for [Vector2fc].
 *
 * The serialized form is a two-element list of single-precision x and y components.
 *
 * ```json
 * [1.25, 2.5]
 * ```
 *
 * @see Vector2fc
 */
@OptIn(InternalSerializationApi::class)
object Vector2fcSerializer : KSerializer<Vector2fc> {
    override val descriptor: SerialDescriptor = buildSerialDescriptor("Vector2fc", StructureKind.LIST)

    override fun serialize(encoder: Encoder, value: Vector2fc) {
        val composite = encoder.beginCollection(descriptor, 2)
        composite.encodeFloatElement(descriptor, 0, value.x())
        composite.encodeFloatElement(descriptor, 1, value.y())
        composite.endStructure(descriptor)
    }

    override fun deserialize(decoder: Decoder): Vector2fc {
        var x: Float? = null
        var y: Float? = null

        decoder.decodeStructure(descriptor) {
            while (true) {
                when (val index = decodeElementIndex(descriptor)) {
                    0 -> x = decodeFloatElement(descriptor, 0)
                    1 -> y = decodeFloatElement(descriptor, 1)
                    CompositeDecoder.DECODE_DONE -> break
                    else -> throw SerializationException("Vector2fc index must be between 0 and 1, got: $index")
                }
            }
        }

        return Vector2f(
            x ?: throw SerializationException("Vector2fc must contain x"),
            y ?: throw SerializationException("Vector2fc must contain y"),
        )
    }
}
