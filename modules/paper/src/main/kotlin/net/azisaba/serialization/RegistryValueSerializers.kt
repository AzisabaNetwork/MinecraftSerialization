package net.azisaba.serialization

import io.papermc.paper.datacomponent.DataComponentType
import io.papermc.paper.dialog.Dialog
import io.papermc.paper.registry.RegistryKey
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import org.bukkit.Art
import org.bukkit.Fluid
import org.bukkit.GameEvent
import org.bukkit.GameRule
import org.bukkit.JukeboxSong
import org.bukkit.MusicInstrument
import org.bukkit.Particle
import org.bukkit.Sound
import org.bukkit.attribute.Attribute
import org.bukkit.block.Biome
import org.bukkit.block.BlockType
import org.bukkit.block.banner.PatternType
import org.bukkit.damage.DamageType
import org.bukkit.enchantments.Enchantment
import org.bukkit.entity.Cat
import org.bukkit.entity.Chicken
import org.bukkit.entity.Cow
import org.bukkit.entity.EntityType
import org.bukkit.entity.Frog
import org.bukkit.entity.Pig
import org.bukkit.entity.Villager
import org.bukkit.entity.Wolf
import org.bukkit.entity.ZombieNautilus
import org.bukkit.entity.memory.MemoryKey
import org.bukkit.generator.structure.Structure
import org.bukkit.generator.structure.StructureType
import org.bukkit.inventory.ItemType
import org.bukkit.inventory.MenuType
import org.bukkit.inventory.meta.trim.TrimMaterial
import org.bukkit.inventory.meta.trim.TrimPattern
import org.bukkit.map.MapCursor
import org.bukkit.potion.PotionEffectType
import org.bukkit.potion.PotionType

object GameEventSerializer : RegistryValueSerializer<GameEvent>(RegistryKey.GAME_EVENT) {
    override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor("GameEvent", PrimitiveKind.STRING)
}

object StructureTypeSerializer : RegistryValueSerializer<StructureType>(RegistryKey.STRUCTURE_TYPE) {
    override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor("StructureType", PrimitiveKind.STRING)
}

object PotionEffectTypeSerializer : RegistryValueSerializer<PotionEffectType>(RegistryKey.MOB_EFFECT) {
    override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor("PotionEffectType", PrimitiveKind.STRING)
}

object BlockTypeSerializer : RegistryValueSerializer<BlockType>(RegistryKey.BLOCK) {
    override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor("BlockType", PrimitiveKind.STRING)
}

object ItemTypeSerializer : RegistryValueSerializer<ItemType>(RegistryKey.ITEM) {
    override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor("ItemType", PrimitiveKind.STRING)
}

object VillagerProfessionSerializer : RegistryValueSerializer<Villager.Profession>(RegistryKey.VILLAGER_PROFESSION) {
    override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor("VillagerProfession", PrimitiveKind.STRING)
}

object VillagerTypeSerializer : RegistryValueSerializer<Villager.Type>(RegistryKey.VILLAGER_TYPE) {
    override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor("VillagerType", PrimitiveKind.STRING)
}

object MapDecorationTypeSerializer : RegistryValueSerializer<MapCursor.Type>(RegistryKey.MAP_DECORATION_TYPE) {
    override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor("MapDecorationType", PrimitiveKind.STRING)
}

object MenuTypeSerializer : RegistryValueSerializer<MenuType>(RegistryKey.MENU) {
    override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor("MenuType", PrimitiveKind.STRING)
}

object AttributeSerializer : RegistryValueSerializer<Attribute>(RegistryKey.ATTRIBUTE) {
    override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor("Attribute", PrimitiveKind.STRING)
}

object FluidSerializer : RegistryValueSerializer<Fluid>(RegistryKey.FLUID) {
    override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor("Fluid", PrimitiveKind.STRING)
}

object SoundSerializer : RegistryValueSerializer<Sound>(RegistryKey.SOUND_EVENT) {
    override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor("Sound", PrimitiveKind.STRING)
}

object DataComponentTypeSerializer : RegistryValueSerializer<DataComponentType>(RegistryKey.DATA_COMPONENT_TYPE) {
    override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor("DataComponentType", PrimitiveKind.STRING)
}

object GameRuleSerializer : RegistryValueSerializer<GameRule<*>>(RegistryKey.GAME_RULE) {
    override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor("GameRule", PrimitiveKind.STRING)
}

object BiomeSerializer : RegistryValueSerializer<Biome>(RegistryKey.BIOME) {
    override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor("Biome", PrimitiveKind.STRING)
}

object StructureSerializer : RegistryValueSerializer<Structure>(RegistryKey.STRUCTURE) {
    override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor("Structure", PrimitiveKind.STRING)
}

object TrimMaterialSerializer : RegistryValueSerializer<TrimMaterial>(RegistryKey.TRIM_MATERIAL) {
    override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor("TrimMaterial", PrimitiveKind.STRING)
}

object TrimPatternSerializer : RegistryValueSerializer<TrimPattern>(RegistryKey.TRIM_PATTERN) {
    override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor("TrimPattern", PrimitiveKind.STRING)
}

object DamageTypeSerializer : RegistryValueSerializer<DamageType>(RegistryKey.DAMAGE_TYPE) {
    override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor("DamageType", PrimitiveKind.STRING)
}

object WolfVariantSerializer : RegistryValueSerializer<Wolf.Variant>(RegistryKey.WOLF_VARIANT) {
    override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor("WolfVariant", PrimitiveKind.STRING)
}

object WolfSoundVariantSerializer : RegistryValueSerializer<Wolf.SoundVariant>(RegistryKey.WOLF_SOUND_VARIANT) {
    override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor("WolfSoundVariant", PrimitiveKind.STRING)
}

object EnchantmentSerializer : RegistryValueSerializer<Enchantment>(RegistryKey.ENCHANTMENT) {
    override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor("Enchantment", PrimitiveKind.STRING)
}

object JukeboxSongSerializer : RegistryValueSerializer<JukeboxSong>(RegistryKey.JUKEBOX_SONG) {
    override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor("JukeboxSong", PrimitiveKind.STRING)
}

object BannerPatternSerializer : RegistryValueSerializer<PatternType>(RegistryKey.BANNER_PATTERN) {
    override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor("BannerPattern", PrimitiveKind.STRING)
}

object PaintingVariantSerializer : RegistryValueSerializer<Art>(RegistryKey.PAINTING_VARIANT) {
    override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor("PaintingVariant", PrimitiveKind.STRING)
}

object InstrumentSerializer : RegistryValueSerializer<MusicInstrument>(RegistryKey.INSTRUMENT) {
    override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor("Instrument", PrimitiveKind.STRING)
}

object CatVariantSerializer : RegistryValueSerializer<Cat.Type>(RegistryKey.CAT_VARIANT) {
    override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor("CatVariant", PrimitiveKind.STRING)
}

object FrogVariantSerializer : RegistryValueSerializer<Frog.Variant>(RegistryKey.FROG_VARIANT) {
    override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor("FrogVariant", PrimitiveKind.STRING)
}

object ChickenVariantSerializer : RegistryValueSerializer<Chicken.Variant>(RegistryKey.CHICKEN_VARIANT) {
    override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor("ChickenVariant", PrimitiveKind.STRING)
}

object CowVariantSerializer : RegistryValueSerializer<Cow.Variant>(RegistryKey.COW_VARIANT) {
    override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor("CowVariant", PrimitiveKind.STRING)
}

object PigVariantSerializer : RegistryValueSerializer<Pig.Variant>(RegistryKey.PIG_VARIANT) {
    override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor("PigVariant", PrimitiveKind.STRING)
}

object ZombieNautilusVariantSerializer : RegistryValueSerializer<ZombieNautilus.Variant>(RegistryKey.ZOMBIE_NAUTILUS_VARIANT) {
    override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor("ZombieNautilusVariant", PrimitiveKind.STRING)
}

object DialogSerializer : RegistryValueSerializer<Dialog>(RegistryKey.DIALOG) {
    override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor("Dialog", PrimitiveKind.STRING)
}

object EntityTypeSerializer : RegistryValueSerializer<EntityType>(RegistryKey.ENTITY_TYPE) {
    override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor("EntityType", PrimitiveKind.STRING)
}

object ParticleSerializer : RegistryValueSerializer<Particle>(RegistryKey.PARTICLE_TYPE) {
    override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor("Particle", PrimitiveKind.STRING)
}

object PotionTypeSerializer : RegistryValueSerializer<PotionType>(RegistryKey.POTION) {
    override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor("PotionType", PrimitiveKind.STRING)
}

object MemoryKeySerializer : RegistryValueSerializer<MemoryKey<*>>(RegistryKey.MEMORY_MODULE_TYPE) {
    override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor("MemoryKey", PrimitiveKind.STRING)
}
