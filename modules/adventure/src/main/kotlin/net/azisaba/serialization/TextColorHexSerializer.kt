package net.azisaba.serialization

import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import net.kyori.adventure.text.format.TextColor

object TextColorHexSerializer : KSerializer<TextColor> {
    override val descriptor: SerialDescriptor = RGBHexSerializer.descriptor

    override fun serialize(encoder: Encoder, value: TextColor) {
        RGBHexSerializer.serialize(encoder, value)
    }

    override fun deserialize(decoder: Decoder): TextColor {
        return TextColor.color(RGBHexSerializer.deserialize(decoder))
    }
}
