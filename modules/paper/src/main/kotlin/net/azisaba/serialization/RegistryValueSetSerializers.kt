package net.azisaba.serialization

import io.papermc.paper.registry.RegistryKey
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.builtins.serializer
import kotlinx.serialization.descriptors.SerialDescriptor
import org.bukkit.Art
import org.bukkit.Fluid
import org.bukkit.GameEvent
import org.bukkit.block.Biome
import org.bukkit.block.BlockType
import org.bukkit.block.banner.PatternType
import org.bukkit.damage.DamageType
import org.bukkit.enchantments.Enchantment
import org.bukkit.entity.EntityType
import org.bukkit.generator.structure.Structure
import org.bukkit.inventory.ItemType
import org.bukkit.MusicInstrument

object GameEventSetSerializer : RegistryValueSetSerializer<GameEvent>(RegistryKey.GAME_EVENT) {
    override val descriptor: SerialDescriptor = ListSerializer(String.serializer()).descriptor
}

object BlockTypeSetSerializer : RegistryValueSetSerializer<BlockType>(RegistryKey.BLOCK) {
    override val descriptor: SerialDescriptor = ListSerializer(String.serializer()).descriptor
}

object ItemTypeSetSerializer : RegistryValueSetSerializer<ItemType>(RegistryKey.ITEM) {
    override val descriptor: SerialDescriptor = ListSerializer(String.serializer()).descriptor
}

object FluidSetSerializer : RegistryValueSetSerializer<Fluid>(RegistryKey.FLUID) {
    override val descriptor: SerialDescriptor = ListSerializer(String.serializer()).descriptor
}

object BiomeSetSerializer : RegistryValueSetSerializer<Biome>(RegistryKey.BIOME) {
    override val descriptor: SerialDescriptor = ListSerializer(String.serializer()).descriptor
}

object StructureSetSerializer : RegistryValueSetSerializer<Structure>(RegistryKey.STRUCTURE) {
    override val descriptor: SerialDescriptor = ListSerializer(String.serializer()).descriptor
}

object DamageTypeSetSerializer : RegistryValueSetSerializer<DamageType>(RegistryKey.DAMAGE_TYPE) {
    override val descriptor: SerialDescriptor = ListSerializer(String.serializer()).descriptor
}

object EnchantmentSetSerializer : RegistryValueSetSerializer<Enchantment>(RegistryKey.ENCHANTMENT) {
    override val descriptor: SerialDescriptor = ListSerializer(String.serializer()).descriptor
}

object BannerPatternSetSerializer : RegistryValueSetSerializer<PatternType>(RegistryKey.BANNER_PATTERN) {
    override val descriptor: SerialDescriptor = ListSerializer(String.serializer()).descriptor
}

object PaintingVariantSetSerializer : RegistryValueSetSerializer<Art>(RegistryKey.PAINTING_VARIANT) {
    override val descriptor: SerialDescriptor = ListSerializer(String.serializer()).descriptor
}

object InstrumentSetSerializer : RegistryValueSetSerializer<MusicInstrument>(RegistryKey.INSTRUMENT) {
    override val descriptor: SerialDescriptor = ListSerializer(String.serializer()).descriptor
}

object EntityTypeSetSerializer : RegistryValueSetSerializer<EntityType>(RegistryKey.ENTITY_TYPE) {
    override val descriptor: SerialDescriptor = ListSerializer(String.serializer()).descriptor
}
