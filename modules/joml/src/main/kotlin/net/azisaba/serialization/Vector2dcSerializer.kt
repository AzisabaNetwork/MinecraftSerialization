package net.azisaba.serialization

import kotlinx.serialization.InternalSerializationApi
import kotlinx.serialization.KSerializer
import kotlinx.serialization.SerializationException
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.descriptors.StructureKind
import kotlinx.serialization.descriptors.buildSerialDescriptor
import kotlinx.serialization.encoding.*
import org.joml.Vector2d
import org.joml.Vector2dc

/**
 * A serializer implementation for [Vector2dc].
 *
 * The serialized form is a two-element list of double-precision x and y components.
 *
 * ```json
 * [1.25, 2.5]
 * ```
 *
 * @see Vector2dc
 */
object Vector2dcSerializer : KSerializer<Vector2dc> {
    @OptIn(InternalSerializationApi::class)
    override val descriptor: SerialDescriptor = buildSerialDescriptor("Vector2dc", StructureKind.LIST)

    override fun serialize(encoder: Encoder, value: Vector2dc) {
        encoder.encodeCollection(descriptor, 2) {
            encodeDoubleElement(descriptor, 0, value.x())
            encodeDoubleElement(descriptor, 1, value.y())
        }
    }

    override fun deserialize(decoder: Decoder): Vector2dc {
        return decoder.decodeStructure(descriptor) {
            var x: Double? = null
            var y: Double? = null

            while (true) {
                when (val index = decodeElementIndex(descriptor)) {
                    0 -> x = decodeDoubleElement(descriptor, index)
                    1 -> y = decodeDoubleElement(descriptor, index)
                    CompositeDecoder.DECODE_DONE -> break
                    else -> throw SerializationException("Unexpected index: $index")
                }
            }

            Vector2d(
                x ?: throw SerializationException("Vector2dc must contain x"),
                y ?: throw SerializationException("Vector2dc must contain y"),
            )
        }
    }
}
