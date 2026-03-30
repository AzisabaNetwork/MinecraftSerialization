package net.azisaba.serialization

import io.papermc.paper.registry.RegistryKey
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import org.bukkit.inventory.ItemType

object ItemTypeSerializer : RegistryValueSerializer<ItemType>(RegistryKey.ITEM) {
    override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor("ItemType", PrimitiveKind.STRING)
}
