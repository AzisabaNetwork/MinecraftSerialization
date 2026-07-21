package net.azisaba.serialization

import kotlinx.serialization.InternalSerializationApi
import kotlinx.serialization.KSerializer
import kotlinx.serialization.SerializationException
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.descriptors.StructureKind
import kotlinx.serialization.descriptors.buildSerialDescriptor
import kotlinx.serialization.encoding.*
import org.joml.Quaternionf
import org.joml.Quaternionfc

/**
 * A serializer implementation for [Quaternionfc].
 *
 * The serialized form is a four-element list of single-precision x, y, z, and w components.
 *
 * ```json
 * [0.0, 0.0, 0.0, 1.0]
 * ```
 *
 * @see Quaternionfc
 */
object QuaternionfcSerializer : KSerializer<Quaternionfc> {
    @OptIn(InternalSerializationApi::class)
    override val descriptor: SerialDescriptor = buildSerialDescriptor("Quaternionfc", StructureKind.LIST)

    override fun serialize(encoder: Encoder, value: Quaternionfc) {
        encoder.encodeCollection(descriptor, 4) {
            encodeFloatElement(descriptor, 0, value.x())
            encodeFloatElement(descriptor, 1, value.y())
            encodeFloatElement(descriptor, 2, value.z())
            encodeFloatElement(descriptor, 3, value.w())
        }
    }

    override fun deserialize(decoder: Decoder): Quaternionfc {
        return decoder.decodeStructure(descriptor) {
            var x: Float? = null
            var y: Float? = null
            var z: Float? = null
            var w: Float? = null

            while (true) {
                when (val index = decodeElementIndex(descriptor)) {
                    0 -> x = decodeFloatElement(descriptor, index)
                    1 -> y = decodeFloatElement(descriptor, index)
                    2 -> z = decodeFloatElement(descriptor, index)
                    3 -> w = decodeFloatElement(descriptor, index)
                    CompositeDecoder.DECODE_DONE -> break
                    else -> throw SerializationException("Unexpected index: $index")
                }
            }

            Quaternionf(
                x ?: throw SerializationException("Quaternionfc must contain x"),
                y ?: throw SerializationException("Quaternionfc must contain y"),
                z ?: throw SerializationException("Quaternionfc must contain z"),
                w ?: throw SerializationException("Quaternionfc must contain w"),
            )
        }
    }
}
