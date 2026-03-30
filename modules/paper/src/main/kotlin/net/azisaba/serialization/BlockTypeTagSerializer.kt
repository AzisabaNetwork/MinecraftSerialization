package net.azisaba.serialization

import io.papermc.paper.registry.RegistryKey
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import org.bukkit.block.BlockType

object BlockTypeTagSerializer : RegistryTagSerializer<BlockType>(RegistryKey.BLOCK) {
    override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor("BlockType", PrimitiveKind.STRING)
}
