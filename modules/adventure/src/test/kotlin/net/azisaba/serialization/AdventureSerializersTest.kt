package net.azisaba.serialization

import kotlinx.serialization.SerializationException
import kotlinx.serialization.json.Json
import net.kyori.adventure.key.Key
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
    fun componentJsonSerializerRoundTrips() {
        val component = Component.text("hello").color(TextColor.color(0x12, 0x34, 0x56))

        val encoded = json.encodeToString(ComponentJsonSerializer(), component)
        val decoded = json.decodeFromString(ComponentJsonSerializer(), encoded)

        assertEquals(component, decoded)
    }

    @Test
    fun componentMiniMessageSerializerRoundTrips() {
        val serializer = ComponentMiniMessageSerializer()
        val encoded = json.encodeToString(serializer, Component.text("hello"))
        val decoded = json.decodeFromString(serializer, encoded)

        assertEquals(Component.text("hello"), decoded)
        assertEquals("\"hello\"", encoded)
    }

    @Test
    fun rgbLikeIntSerializerRoundTrips() {
        val color = TextColor.color(0x12, 0x34, 0x56)

        val encoded = json.encodeToString(RGBIntSerializer, color)
        val decoded = json.decodeFromString(RGBIntSerializer, encoded)

        assertEquals("1193046", encoded)
        assertEquals(color, decoded)
    }

    @Test
    fun rgbLikeHexSerializerRoundTrips() {
        val color = TextColor.color(0x12, 0x34, 0x56)

        val encoded = json.encodeToString(RGBHexSerializer, color)
        val decoded = json.decodeFromString(RGBHexSerializer, encoded)

        assertEquals("\"#123456\"", encoded)
        assertEquals(color, decoded)
    }

    @Test
    fun rgbLikeArraySerializerRoundTrips() {
        val color = TextColor.color(0x12, 0x34, 0x56)

        val encoded = json.encodeToString(RGBArraySerializer, color)
        val decoded = json.decodeFromString(RGBArraySerializer, encoded)

        assertEquals("[18,52,86]", encoded)
        assertEquals(color, decoded)
    }

    @Test
    fun argbLikeIntSerializerRoundTrips() {
        val color = ShadowColor.shadowColor(0x12, 0x34, 0x56, 0x78)

        val encoded = json.encodeToString(ARGBIntSerializer, color)
        val decoded = json.decodeFromString(ARGBIntSerializer, encoded)

        assertEquals("2014458966", encoded)
        assertEquals(color, decoded)
    }

    @Test
    fun argbLikeHexSerializerRoundTrips() {
        val color = ShadowColor.shadowColor(0x12, 0x34, 0x56, 0x78)

        val encoded = json.encodeToString(ARGBHexSerializer, color)
        val decoded = json.decodeFromString(ARGBHexSerializer, encoded)

        assertEquals("\"#12345678\"", encoded)
        assertEquals(color, decoded)
    }

    @Test
    fun argbLikeArraySerializerRoundTrips() {
        val color = ShadowColor.shadowColor(0x12, 0x34, 0x56, 0x78)

        val encoded = json.encodeToString(ARGBArraySerializer, color)
        val decoded = json.decodeFromString(ARGBArraySerializer, encoded)

        assertEquals("[18,52,86,120]", encoded)
        assertEquals(color, decoded)
    }

    @Test
    fun rgbLikeHexSerializerRejectsInvalidHex() {
        assertFailsWith<SerializationException> {
            json.decodeFromString(RGBHexSerializer, "\"#12345\"")
        }
    }

    @Test
    fun argbLikeArraySerializerRejectsMissingAlpha() {
        assertFailsWith<SerializationException> {
            json.decodeFromString(ARGBArraySerializer, "[18,52,86]")
        }
    }
}
