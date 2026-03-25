package net.azisaba.serialization

import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import net.kyori.adventure.text.format.TextColor

object TextColorIntSerializer : KSerializer<TextColor> {
    override val descriptor: SerialDescriptor = RGBIntSerializer.descriptor

    override fun serialize(encoder: Encoder, value: TextColor) {
        RGBIntSerializer.serialize(encoder, value)
    }

    override fun deserialize(decoder: Decoder): TextColor {
        return TextColor.color(RGBIntSerializer.deserialize(decoder))
    }
}
