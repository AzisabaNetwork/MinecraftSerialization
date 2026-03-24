package net.azisaba.serialization

import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.minimessage.MiniMessage

class ComponentMiniMessageSerializer(
    private val miniMessage: MiniMessage = MiniMessage.miniMessage(),
) : KSerializer<Component> {
    override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor("ComponentMiniMessage", PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, value: Component) {
        val string = miniMessage.serialize(value)
        encoder.encodeString(string)
    }

    override fun deserialize(decoder: Decoder): Component {
        val string = decoder.decodeString()
        return miniMessage.deserialize(string)
    }
}
