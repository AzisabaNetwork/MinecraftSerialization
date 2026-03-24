package net.azisaba.serialization

import kotlinx.serialization.InternalSerializationApi
import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.descriptors.SerialKind
import kotlinx.serialization.descriptors.buildSerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonDecoder
import kotlinx.serialization.json.JsonEncoder
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.serializer.json.JSONComponentSerializer

@OptIn(InternalSerializationApi::class)
class ComponentJsonSerializer(
    private val json: Json = Json.Default,
    private val adventure: JSONComponentSerializer = JSONComponentSerializer.json(),
) : KSerializer<Component> {
    override val descriptor: SerialDescriptor = buildSerialDescriptor("Component", SerialKind.CONTEXTUAL)

    override fun serialize(encoder: Encoder, value: Component) {
        require(encoder is JsonEncoder) {
            "ComponentJsonSerializer supports only JSON. Found: ${encoder::class}"
        }

        val jsonString = adventure.serialize(value)
        val jsonElement = json.parseToJsonElement(jsonString)

        encoder.encodeJsonElement(jsonElement)
    }

    override fun deserialize(decoder: Decoder): Component {
        require(decoder is JsonDecoder) {
            "ComponentJsonSerializer supports only JSON. Found: ${decoder::class}"
        }

        val jsonElement = decoder.decodeJsonElement()
        val jsonString = jsonElement.toString()

        return adventure.deserialize(jsonString)
    }
}
