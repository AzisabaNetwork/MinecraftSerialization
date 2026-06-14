package net.azisaba.serialization

import io.papermc.paper.math.BlockPosition
import io.papermc.paper.math.Position
import kotlinx.serialization.KSerializer
import kotlinx.serialization.SerializationException
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.descriptors.buildClassSerialDescriptor
import kotlinx.serialization.descriptors.element
import kotlinx.serialization.encoding.CompositeDecoder
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.encoding.decodeStructure
import kotlinx.serialization.encoding.encodeStructure

/**
 * A serializer implementation for [BlockPosition].
 *
 * The serialized form is an object containing the integer coordinates.
 *
 * ```json
 * {
 *   "x": 10,
 *   "y": 64,
 *   "z": -20
 * }
 * ```
 *
 * @see BlockPosition
 */
object BlockPositionSerializer : KSerializer<BlockPosition> {
    override val descriptor: SerialDescriptor = buildClassSerialDescriptor("BlockPosition") {
        element<Int>("x")
        element<Int>("y")
        element<Int>("z")
    }

    override fun serialize(encoder: Encoder, value: BlockPosition) {
        encoder.encodeStructure(descriptor) {
            encodeIntElement(descriptor, 0, value.blockX())
            encodeIntElement(descriptor, 1, value.blockY())
            encodeIntElement(descriptor, 2, value.blockZ())
        }
    }

    override fun deserialize(decoder: Decoder): BlockPosition {
        var x: Int? = null
        var y: Int? = null
        var z: Int? = null

        decoder.decodeStructure(descriptor) {
            while (true) {
                when (val index = decodeElementIndex(descriptor)) {
                    0 -> x = decodeIntElement(descriptor, 0)
                    1 -> y = decodeIntElement(descriptor, 1)
                    2 -> z = decodeIntElement(descriptor, 2)
                    CompositeDecoder.DECODE_DONE -> break
                    else -> error("Unexpected index: $index")
                }
            }
        }

        return Position.block(
            x ?: throw SerializationException("x cannot be null"),
            y ?: throw SerializationException("y cannot be null"),
            z ?: throw SerializationException("z cannot be null"),
        )
    }
}
