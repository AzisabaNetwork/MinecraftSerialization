package net.azisaba.serialization

import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import net.kyori.adventure.text.format.ShadowColor

object ShadowColorIntSerializer : KSerializer<ShadowColor> {
    override val descriptor: SerialDescriptor = ARGBIntSerializer.descriptor

    override fun serialize(encoder: Encoder, value: ShadowColor) {
        ARGBIntSerializer.serialize(encoder, value)
    }

    override fun deserialize(decoder: Decoder): ShadowColor {
        return ShadowColor.shadowColor(ARGBIntSerializer.deserialize(decoder))
    }
}
