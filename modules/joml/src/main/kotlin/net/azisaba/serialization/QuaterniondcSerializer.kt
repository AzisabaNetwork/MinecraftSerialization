package net.azisaba.serialization

import kotlinx.serialization.InternalSerializationApi
import kotlinx.serialization.KSerializer
import kotlinx.serialization.SerializationException
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.descriptors.StructureKind
import kotlinx.serialization.descriptors.buildSerialDescriptor
import kotlinx.serialization.encoding.*
import org.joml.Quaterniond
import org.joml.Quaterniondc

/**
 * A serializer implementation for [Quaterniondc].
 *
 * The serialized form is a four-element list of double-precision x, y, z, and w components.
 *
 * ```json
 * [0.0, 0.0, 0.0, 1.0]
 * ```
 *
 * @see Quaterniondc
 */
object QuaterniondcSerializer : KSerializer<Quaterniondc> {
    @OptIn(InternalSerializationApi::class)
    override val descriptor: SerialDescriptor = buildSerialDescriptor("Quaterniondc", StructureKind.LIST)

    override fun serialize(encoder: Encoder, value: Quaterniondc) {
        encoder.encodeCollection(descriptor, 4) {
            encodeDoubleElement(descriptor, 0, value.x())
            encodeDoubleElement(descriptor, 1, value.y())
            encodeDoubleElement(descriptor, 2, value.z())
            encodeDoubleElement(descriptor, 3, value.w())
        }
    }

    override fun deserialize(decoder: Decoder): Quaterniondc {
        return decoder.decodeStructure(descriptor) {
            var x: Double? = null
            var y: Double? = null
            var z: Double? = null
            var w: Double? = null

            while (true) {
                when (val index = decodeElementIndex(descriptor)) {
                    0 -> x = decodeDoubleElement(descriptor, index)
                    1 -> y = decodeDoubleElement(descriptor, index)
                    2 -> z = decodeDoubleElement(descriptor, index)
                    3 -> w = decodeDoubleElement(descriptor, index)
                    CompositeDecoder.DECODE_DONE -> break
                    else -> throw SerializationException("Unexpected index: $index")
                }
            }

            Quaterniond(
                x ?: throw SerializationException("Quaterniondc must contain x"),
                y ?: throw SerializationException("Quaterniondc must contain y"),
                z ?: throw SerializationException("Quaterniondc must contain z"),
                w ?: throw SerializationException("Quaterniondc must contain w"),
            )
        }
    }
}
