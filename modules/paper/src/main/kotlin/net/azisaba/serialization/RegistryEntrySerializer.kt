package net.azisaba.serialization

import io.papermc.paper.registry.RegistryAccess
import io.papermc.paper.registry.RegistryKey
import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import org.bukkit.Keyed
import org.jetbrains.annotations.ApiStatus

/**
 * A serializer implementation for [Keyed].
 *
 * The serialized form is the entry's key.
 *
 * ```json
 * "minecraft:stone"
 * ```
 *
 * @param T registry entry type
 * @see Keyed
 * @see RegistryKey
 */
@ApiStatus.NonExtendable
abstract class RegistryEntrySerializer<T : Keyed>(
    name: String, val registryKey: RegistryKey<T>,
) : KSerializer<T> {
    override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor(name, PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, value: T) {
        val registry = RegistryAccess.registryAccess().getRegistry(registryKey)
        encoder.encodeSerializableValue(KeySerializer, registry.getKeyOrThrow(value))
    }

    override fun deserialize(decoder: Decoder): T {
        val registry = RegistryAccess.registryAccess().getRegistry(registryKey)
        return registry.getOrThrow(decoder.decodeSerializableValue(KeySerializer))
    }
}
