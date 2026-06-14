package net.azisaba.serialization

import io.papermc.paper.math.FinePosition
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
 * A serializer implementation for [FinePosition].
 *
 * The serialized form is an object containing the floating-point coordinates.
 *
 * ```json
 * {
 *   "x": 10.5,
 *   "y": 64.0,
 *   "z": -20.25
 * }
 * ```
 *
 * @see FinePosition
 */
object FinePositionSerializer : KSerializer<FinePosition> {
    override val descriptor: SerialDescriptor = buildClassSerialDescriptor("FinePosition") {
        element<Double>("x")
        element<Double>("y")
        element<Double>("z")
    }

    override fun serialize(encoder: Encoder, value: FinePosition) {
        encoder.encodeStructure(descriptor) {
            encodeDoubleElement(descriptor, 0, value.x())
            encodeDoubleElement(descriptor, 1, value.y())
            encodeDoubleElement(descriptor, 2, value.z())
        }
    }

    override fun deserialize(decoder: Decoder): FinePosition {
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
                    else -> throw SerializationException("Unexcepted index: $index")
                }
            }
        }

        return Position.fine(
            x ?: throw SerializationException("x cannot be null"),
            y ?: throw SerializationException("y cannot be null"),
            z ?: throw SerializationException("z cannot be null"),
        )
    }
}
