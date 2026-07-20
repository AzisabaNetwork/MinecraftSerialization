package net.azisaba.serialization

import kotlinx.serialization.KSerializer
import kotlinx.serialization.SerializationException
import kotlinx.serialization.descriptors.*
import kotlinx.serialization.encoding.*
import net.kyori.adventure.key.Key
import net.kyori.adventure.sound.Sound

/**
 * A serializer implementation for [Sound].
 *
 * The serialized form is an object containing the sound type, source, volume, and pitch.
 *
 * ```json
 * {
 *   "type": "minecraft:block.note_block.harp",
 *   "source": "master",
 *   "volume": 1.0,
 *   "pitch": 1.0
 * }
 * ```
 *
 * @see Sound
 */
object SoundSerializer : KSerializer<Sound> {
    override val descriptor = buildClassSerialDescriptor("Sound") {
        element("type", KeySerializer.descriptor)
        element("source", SoundSourceSerializer.descriptor, isOptional = true)
        element<Float>("volume", isOptional = true)
        element<Float>("pitch", isOptional = true)
    }

    override fun serialize(encoder: Encoder, value: Sound) {
        encoder.encodeStructure(descriptor) {
            encodeSerializableElement(descriptor, 0, KeySerializer, value.name())
            encodeSerializableElement(descriptor, 1, SoundSourceSerializer, value.source())
            encodeFloatElement(descriptor, 2, value.volume())
            encodeFloatElement(descriptor, 3, value.pitch())
        }
    }

    override fun deserialize(decoder: Decoder): Sound {
        return decoder.decodeStructure(descriptor) {
            var type: Key? = null
            var source = Sound.Source.MASTER
            var volume = 1.0f
            var pitch = 1.0f

            while (true) {
                when (val index = decodeElementIndex(descriptor)) {
                    0 -> type = decodeSerializableElement(descriptor, index, KeySerializer)
                    1 -> source = decodeSerializableElement(descriptor, index, SoundSourceSerializer)
                    2 -> volume = decodeFloatElement(descriptor, index)
                    3 -> pitch = decodeFloatElement(descriptor, index)
                    CompositeDecoder.DECODE_DONE -> break
                    else -> throw SerializationException("Unexpected index: $index")
                }
            }

            Sound.sound()
                .type(type ?: throw SerializationException("Sound must contain type"))
                .source(source)
                .volume(volume)
                .pitch(pitch)
                .build()
        }
    }
}

/**
 * A serializer implementation for [Sound.Source].
 *
 * The serialized form is the registered source name.
 *
 * ```json
 * "master"
 * ```
 *
 * @see Sound.Source
 */
object SoundSourceSerializer : KSerializer<Sound.Source> {
    override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor("Sound.Source", PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, value: Sound.Source) {
        encoder.encodeString(Sound.Source.NAMES.keyOrThrow(value))
    }

    override fun deserialize(decoder: Decoder): Sound.Source {
        val name = decoder.decodeString()
        return Sound.Source.NAMES.value(name) ?: throw SerializationException("Invalid sound source: $name")
    }
}
