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

    private fun assertRgbEquals(expected: TextColor, actual: net.kyori.adventure.util.RGBLike) {
        assertEquals(expected.red(), actual.red())
        assertEquals(expected.green(), actual.green())
        assertEquals(expected.blue(), actual.blue())
    }

    private fun assertArgbEquals(expected: ShadowColor, actual: net.kyori.adventure.util.ARGBLike) {
        assertEquals(expected.red(), actual.red())
        assertEquals(expected.green(), actual.green())
        assertEquals(expected.blue(), actual.blue())
        assertEquals(expected.alpha(), actual.alpha())
    }

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

        val encoded = json.encodeToString(ComponentJsonSerializer, component)
        val decoded = json.decodeFromString(ComponentJsonSerializer, encoded)

        assertEquals(component, decoded)
    }

    @Test
    fun componentMiniMessageSerializerRoundTrips() {
        val serializer = ComponentMiniMessageSerializer
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
        assertRgbEquals(color, decoded)
    }

    @Test
    fun rgbLikeHexSerializerRoundTrips() {
        val color = TextColor.color(0x12, 0x34, 0x56)

        val encoded = json.encodeToString(RGBHexSerializer, color)
        val decoded = json.decodeFromString(RGBHexSerializer, encoded)

        assertEquals("\"#123456\"", encoded)
        assertRgbEquals(color, decoded)
    }

    @Test
    fun rgbLikeArraySerializerRoundTrips() {
        val color = TextColor.color(0x12, 0x34, 0x56)

        val encoded = json.encodeToString(RGBArraySerializer, color)
        val decoded = json.decodeFromString(RGBArraySerializer, encoded)

        assertEquals("[18,52,86]", encoded)
        assertRgbEquals(color, decoded)
    }

    @Test
    fun textColorSerializersRoundTrip() {
        val color = TextColor.color(0x12, 0x34, 0x56)

        assertEquals(color, json.decodeFromString(TextColorIntSerializer, json.encodeToString(TextColorIntSerializer, color)))
        assertEquals(color, json.decodeFromString(TextColorHexSerializer, json.encodeToString(TextColorHexSerializer, color)))
        assertEquals(color, json.decodeFromString(TextColorArraySerializer, json.encodeToString(TextColorArraySerializer, color)))
    }

    @Test
    fun argbLikeIntSerializerRoundTrips() {
        val color = ShadowColor.shadowColor(0x12, 0x34, 0x56, 0x78)

        val encoded = json.encodeToString(ARGBIntSerializer, color)
        val decoded = json.decodeFromString(ARGBIntSerializer, encoded)

        assertEquals("2014458966", encoded)
        assertArgbEquals(color, decoded)
    }

    @Test
    fun argbLikeHexSerializerRoundTrips() {
        val color = ShadowColor.shadowColor(0x12, 0x34, 0x56, 0x78)

        val encoded = json.encodeToString(ARGBHexSerializer, color)
        val decoded = json.decodeFromString(ARGBHexSerializer, encoded)

        assertEquals("\"#12345678\"", encoded)
        assertArgbEquals(color, decoded)
    }

    @Test
    fun argbLikeArraySerializerRoundTrips() {
        val color = ShadowColor.shadowColor(0x12, 0x34, 0x56, 0x78)

        val encoded = json.encodeToString(ARGBArraySerializer, color)
        val decoded = json.decodeFromString(ARGBArraySerializer, encoded)

        assertEquals("[18,52,86,120]", encoded)
        assertArgbEquals(color, decoded)
    }

    @Test
    fun shadowColorSerializersRoundTrip() {
        val color = ShadowColor.shadowColor(0x12, 0x34, 0x56, 0x78)

        assertEquals(color, json.decodeFromString(ShadowColorIntSerializer, json.encodeToString(ShadowColorIntSerializer, color)))
        assertEquals(color, json.decodeFromString(ShadowColorHexSerializer, json.encodeToString(ShadowColorHexSerializer, color)))
        assertEquals(color, json.decodeFromString(ShadowColorArraySerializer, json.encodeToString(ShadowColorArraySerializer, color)))
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
