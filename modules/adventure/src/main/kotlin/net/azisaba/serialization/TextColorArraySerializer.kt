package net.azisaba.serialization

import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import net.kyori.adventure.text.format.TextColor

object TextColorArraySerializer : KSerializer<TextColor> {
    override val descriptor: SerialDescriptor = RGBArraySerializer.descriptor

    override fun serialize(encoder: Encoder, value: TextColor) {
        RGBArraySerializer.serialize(encoder, value)
    }

    override fun deserialize(decoder: Decoder): TextColor {
        return TextColor.color(RGBArraySerializer.deserialize(decoder))
    }
}
