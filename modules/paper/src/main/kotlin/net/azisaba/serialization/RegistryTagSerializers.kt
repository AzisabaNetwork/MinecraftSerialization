package net.azisaba.serialization

import io.papermc.paper.dialog.Dialog
import io.papermc.paper.registry.RegistryKey
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
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

object GameEventTagSerializer : RegistryTagSerializer<GameEvent>(RegistryKey.GAME_EVENT) {
    override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor("GameEventTag", PrimitiveKind.STRING)
}

object BlockTypeTagSerializer : RegistryTagSerializer<BlockType>(RegistryKey.BLOCK) {
    override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor("BlockTypeTag", PrimitiveKind.STRING)
}

object ItemTypeTagSerializer : RegistryTagSerializer<ItemType>(RegistryKey.ITEM) {
    override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor("ItemTypeTag", PrimitiveKind.STRING)
}

object FluidTagSerializer : RegistryTagSerializer<Fluid>(RegistryKey.FLUID) {
    override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor("FluidTag", PrimitiveKind.STRING)
}

object BiomeTagSerializer : RegistryTagSerializer<Biome>(RegistryKey.BIOME) {
    override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor("BiomeTag", PrimitiveKind.STRING)
}

object StructureTagSerializer : RegistryTagSerializer<Structure>(RegistryKey.STRUCTURE) {
    override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor("StructureTag", PrimitiveKind.STRING)
}

object DamageTypeTagSerializer : RegistryTagSerializer<DamageType>(RegistryKey.DAMAGE_TYPE) {
    override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor("DamageTypeTag", PrimitiveKind.STRING)
}

object EnchantmentTagSerializer : RegistryTagSerializer<Enchantment>(RegistryKey.ENCHANTMENT) {
    override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor("EnchantmentTag", PrimitiveKind.STRING)
}

object BannerPatternTagSerializer : RegistryTagSerializer<PatternType>(RegistryKey.BANNER_PATTERN) {
    override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor("BannerPatternTag", PrimitiveKind.STRING)
}

object PaintingVariantTagSerializer : RegistryTagSerializer<Art>(RegistryKey.PAINTING_VARIANT) {
    override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor("PaintingVariantTag", PrimitiveKind.STRING)
}

object InstrumentTagSerializer : RegistryTagSerializer<MusicInstrument>(RegistryKey.INSTRUMENT) {
    override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor("InstrumentTag", PrimitiveKind.STRING)
}

object DialogTagSerializer : RegistryTagSerializer<Dialog>(RegistryKey.DIALOG) {
    override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor("DialogTag", PrimitiveKind.STRING)
}

object EntityTypeTagSerializer : RegistryTagSerializer<EntityType>(RegistryKey.ENTITY_TYPE) {
    override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor("EntityTypeTag", PrimitiveKind.STRING)
}
