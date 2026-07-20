package net.azisaba.serialization

import kotlinx.serialization.KSerializer
import kotlinx.serialization.SerializationException
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.descriptors.buildClassSerialDescriptor
import kotlinx.serialization.descriptors.element
import kotlinx.serialization.encoding.*
import net.kyori.adventure.key.Key
import org.bukkit.Bukkit
import org.bukkit.Location

/**
 * A serializer implementation for [Location].
 *
 * The serialized form is an object containing the world key and coordinates.
 *
 * ```json
 * {
 *   "world": "minecraft:overworld",
 *   "x": 10.5,
 *   "y": 64.0,
 *   "z": -20.25
 * }
 * ```
 *
 * @see Location
 */
object LocationSerializer : KSerializer<Location> {
    override val descriptor: SerialDescriptor = buildClassSerialDescriptor("Location") {
        element("world", KeySerializer.descriptor)
        element<Double>("x")
        element<Double>("y")
        element<Double>("z")
    }

    override fun serialize(encoder: Encoder, value: Location) {
        encoder.encodeStructure(descriptor) {
            encodeSerializableElement(descriptor, 0, KeySerializer, value.world.key())
            encodeDoubleElement(descriptor, 1, value.x)
            encodeDoubleElement(descriptor, 2, value.y)
            encodeDoubleElement(descriptor, 3, value.z)
        }
    }

    override fun deserialize(decoder: Decoder): Location {
        return decoder.decodeStructure(descriptor) {
            var worldKey: Key? = null
            var x: Double? = null
            var y: Double? = null
            var z: Double? = null

            while (true) {
                when (val index = decodeElementIndex(descriptor)) {
                    0 -> worldKey = decodeSerializableElement(descriptor, 0, KeySerializer)
                    1 -> x = decodeDoubleElement(descriptor, 1)
                    2 -> y = decodeDoubleElement(descriptor, 2)
                    3 -> z = decodeDoubleElement(descriptor, 3)
                    CompositeDecoder.DECODE_DONE -> break
                    else -> throw SerializationException("Unexpected index: $index")
                }
            }

            val world = Bukkit.getWorld(
                worldKey ?: throw SerializationException("Location must contain world")
            ) ?: throw SerializationException("World $worldKey is not loaded")

            Location(
                world,
                x ?: throw SerializationException("Location must contain x"),
                y ?: throw SerializationException("Location must contain y"),
                z ?: throw SerializationException("Location must contain z"),
            )
        }
    }
}
