package net.azisaba.serialization

import kotlinx.serialization.SerializationException
import kotlinx.serialization.json.Json
import net.kyori.adventure.key.Key
import net.kyori.adventure.sound.Sound
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.ShadowColor
import net.kyori.adventure.text.format.TextColor
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class AdventureSerializersTest {
    private val json = Json

    @Test
    fun keySerializerRoundTrips() {
        val key = Key.key("minecraft", "stone")

        val encoded = json.encodeToString(KeySerializer, key)
        val decoded = json.decodeFromString(KeySerializer, encoded)

        assertEquals("\"minecraft:stone\"", encoded)
        assertEquals(key, decoded)
    }

    @Test
    fun componentSerializerRoundTripsMiniMessage() {
        val component = Component.text("hello")

        val encoded = json.encodeToString(ComponentSerializer, component)
        val decoded = json.decodeFromString(ComponentSerializer, encoded)

        assertEquals("\"hello\"", encoded)
        assertEquals(component, decoded)
    }

    @Test
    fun componentSerializerPreservesFormatting() {
        val component = Component.text("hello").color(TextColor.color(0x12, 0x34, 0x56))

        val decoded = json.decodeFromString(
            ComponentSerializer,
            json.encodeToString(ComponentSerializer, component),
        )

        assertEquals(component, decoded)
    }

    @Test
    fun textColorSerializerRoundTripsHex() {
        val color = TextColor.color(0x12, 0x34, 0x56)

        val encoded = json.encodeToString(TextColorSerializer, color)
        val decoded = json.decodeFromString(TextColorSerializer, encoded)

        assertEquals("\"#123456\"", encoded)
        assertEquals(color, decoded)
    }

    @Test
    fun textColorSerializerDeserializesShortHex() {
        val color = TextColor.color(0x000123)

        assertEquals(color, json.decodeFromString(TextColorSerializer, "\"#123\""))
    }

    @Test
    fun textColorSerializerRejectsInvalidHex() {
        assertFailsWith<SerializationException> {
            json.decodeFromString(TextColorSerializer, "\"#nothex\"")
        }
    }

    @Test
    fun shadowColorSerializerRoundTripsHex() {
        val color = ShadowColor.shadowColor(0x12, 0x34, 0x56, 0x78)

        val encoded = json.encodeToString(ShadowColorSerializer, color)
        val decoded = json.decodeFromString(ShadowColorSerializer, encoded)

        assertEquals("\"#12345678\"", encoded)
        assertEquals(color, decoded)
    }

    @Test
    fun shadowColorSerializerRejectsMissingAlpha() {
        assertFailsWith<SerializationException> {
            json.decodeFromString(ShadowColorSerializer, "\"#123456\"")
        }
    }

    @Test
    fun soundSourceSerializerRoundTrips() {
        val encoded = json.encodeToString(SoundSourceSerializer, Sound.Source.RECORD)
        val decoded = json.decodeFromString(SoundSourceSerializer, encoded)

        assertEquals("\"record\"", encoded)
        assertEquals(Sound.Source.RECORD, decoded)
    }

    @Test
    fun soundSourceSerializerRejectsUnknownSource() {
        assertFailsWith<SerializationException> {
            json.decodeFromString(SoundSourceSerializer, "\"unknown\"")
        }
    }

    @Test
    fun soundSerializerRoundTrips() {
        val sound = Sound.sound(
            Key.key("minecraft", "block.note_block.harp"),
            Sound.Source.MUSIC,
            0.75f,
            1.25f,
        )

        val encoded = json.encodeToString(SoundSerializer, sound)
        val decoded = json.decodeFromString(SoundSerializer, encoded)

        assertEquals(
            """{"type":"minecraft:block.note_block.harp","source":"music","volume":0.75,"pitch":1.25}""",
            encoded,
        )
        assertEquals(sound.name(), decoded.name())
        assertEquals(sound.source(), decoded.source())
        assertEquals(sound.volume(), decoded.volume())
        assertEquals(sound.pitch(), decoded.pitch())
    }

    @Test
    fun soundSerializerUsesDefaultsForOptionalFields() {
        val decoded = json.decodeFromString(
            SoundSerializer,
            """{"type":"minecraft:block.note_block.harp"}""",
        )

        assertEquals(Sound.Source.MASTER, decoded.source())
        assertEquals(1.0f, decoded.volume())
        assertEquals(1.0f, decoded.pitch())
    }

    @Test
    fun soundSerializerRequiresType() {
        assertFailsWith<SerializationException> {
            json.decodeFromString(SoundSerializer, "{}")
        }
    }
}
