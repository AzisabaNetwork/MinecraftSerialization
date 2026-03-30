package net.azisaba.serialization

import io.papermc.paper.registry.RegistryKey
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.builtins.serializer
import kotlinx.serialization.descriptors.SerialDescriptor
import org.bukkit.inventory.ItemType

object ItemTypeSetSerializer : RegistryValueSetSerializer<ItemType>(RegistryKey.ITEM) {
    override val descriptor: SerialDescriptor = ListSerializer(String.serializer()).descriptor
}
