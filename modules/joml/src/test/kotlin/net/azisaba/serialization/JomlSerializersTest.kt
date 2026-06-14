package net.azisaba.serialization

import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.SerializationException
import kotlinx.serialization.json.Json
import org.joml.Quaterniond
import org.joml.Quaternionf
import org.joml.Vector2d
import org.joml.Vector2f
import org.joml.Vector2i
import org.joml.Vector3d
import org.joml.Vector3f
import org.joml.Vector3i
import org.joml.Vector4d
import org.joml.Vector4f
import org.joml.Vector4i
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class JomlSerializersTest {
    private val json = Json

    @Test
    fun vector2fcSerializerRoundTrips() {
        val value = Vector2f(1.25f, 2.5f)
        val encoded = json.encodeToString(Vector2fcSerializer, value)
        val decoded = json.decodeFromString(Vector2fcSerializer, encoded)

        assertEquals("[1.25,2.5]", encoded)
        assertEquals(value.x(), decoded.x())
        assertEquals(value.y(), decoded.y())
    }

    @Test
    fun vector2dcSerializerRoundTrips() {
        val value = Vector2d(1.25, 2.5)
        val encoded = json.encodeToString(Vector2dcSerializer, value)
        val decoded = json.decodeFromString(Vector2dcSerializer, encoded)

        assertEquals("[1.25,2.5]", encoded)
        assertEquals(value.x(), decoded.x())
        assertEquals(value.y(), decoded.y())
    }

    @Test
    fun vector2icSerializerRoundTrips() {
        val value = Vector2i(1, 2)
        val encoded = json.encodeToString(Vector2icSerializer, value)
        val decoded = json.decodeFromString(Vector2icSerializer, encoded)

        assertEquals("[1,2]", encoded)
        assertEquals(value.x(), decoded.x())
        assertEquals(value.y(), decoded.y())
    }

    @Test
    fun vector3fcSerializerRoundTrips() {
        val value = Vector3f(1.25f, 2.5f, 3.75f)
        val encoded = json.encodeToString(Vector3fcSerializer, value)
        val decoded = json.decodeFromString(Vector3fcSerializer, encoded)

        assertEquals("[1.25,2.5,3.75]", encoded)
        assertEquals(value.x(), decoded.x())
        assertEquals(value.y(), decoded.y())
        assertEquals(value.z(), decoded.z())
    }

    @Test
    fun vector3dcSerializerRoundTrips() {
        val value = Vector3d(1.25, 2.5, 3.75)
        val encoded = json.encodeToString(Vector3dcSerializer, value)
        val decoded = json.decodeFromString(Vector3dcSerializer, encoded)

        assertEquals("[1.25,2.5,3.75]", encoded)
        assertEquals(value.x(), decoded.x())
        assertEquals(value.y(), decoded.y())
        assertEquals(value.z(), decoded.z())
    }

    @Test
    fun vector3icSerializerRoundTrips() {
        val value = Vector3i(1, 2, 3)
        val encoded = json.encodeToString(Vector3icSerializer, value)
        val decoded = json.decodeFromString(Vector3icSerializer, encoded)

        assertEquals("[1,2,3]", encoded)
        assertEquals(value.x(), decoded.x())
        assertEquals(value.y(), decoded.y())
        assertEquals(value.z(), decoded.z())
    }

    @Test
    fun vector4fcSerializerRoundTrips() {
        val value = Vector4f(1.25f, 2.5f, 3.75f, 4.0f)
        val encoded = json.encodeToString(Vector4fcSerializer, value)
        val decoded = json.decodeFromString(Vector4fcSerializer, encoded)

        assertEquals("[1.25,2.5,3.75,4.0]", encoded)
        assertEquals(value.x(), decoded.x())
        assertEquals(value.y(), decoded.y())
        assertEquals(value.z(), decoded.z())
        assertEquals(value.w(), decoded.w())
    }

    @Test
    fun vector4dcSerializerRoundTrips() {
        val value = Vector4d(1.25, 2.5, 3.75, 4.0)
        val encoded = json.encodeToString(Vector4dcSerializer, value)
        val decoded = json.decodeFromString(Vector4dcSerializer, encoded)

        assertEquals("[1.25,2.5,3.75,4.0]", encoded)
        assertEquals(value.x(), decoded.x())
        assertEquals(value.y(), decoded.y())
        assertEquals(value.z(), decoded.z())
        assertEquals(value.w(), decoded.w())
    }

    @Test
    fun vector4icSerializerRoundTrips() {
        val value = Vector4i(1, 2, 3, 4)
        val encoded = json.encodeToString(Vector4icSerializer, value)
        val decoded = json.decodeFromString(Vector4icSerializer, encoded)

        assertEquals("[1,2,3,4]", encoded)
        assertEquals(value.x(), decoded.x())
        assertEquals(value.y(), decoded.y())
        assertEquals(value.z(), decoded.z())
        assertEquals(value.w(), decoded.w())
    }

    @Test
    fun quaternionfcSerializerRoundTrips() {
        val value = Quaternionf(1.25f, 2.5f, 3.75f, 4.0f)
        val encoded = json.encodeToString(QuaternionfcSerializer, value)
        val decoded = json.decodeFromString(QuaternionfcSerializer, encoded)

        assertEquals("[1.25,2.5,3.75,4.0]", encoded)
        assertEquals(value.x(), decoded.x())
        assertEquals(value.y(), decoded.y())
        assertEquals(value.z(), decoded.z())
        assertEquals(value.w(), decoded.w())
    }

    @Test
    fun quaterniondcSerializerRoundTrips() {
        val value = Quaterniond(1.25, 2.5, 3.75, 4.0)
        val encoded = json.encodeToString(QuaterniondcSerializer, value)
        val decoded = json.decodeFromString(QuaterniondcSerializer, encoded)

        assertEquals("[1.25,2.5,3.75,4.0]", encoded)
        assertEquals(value.x(), decoded.x())
        assertEquals(value.y(), decoded.y())
        assertEquals(value.z(), decoded.z())
        assertEquals(value.w(), decoded.w())
    }

    @Test
    fun vectorSerializerRejectsMissingComponents() {
        assertFailsWith<SerializationException> {
            json.decodeFromString(Vector3fcSerializer, "[1.0,2.0]")
        }
    }

    @Test
    fun vectorSerializerRejectsExtraComponents() {
        assertFailsWith<SerializationException> {
            json.decodeFromString(Vector2icSerializer, "[1,2,3]")
        }
    }

    @Test
    fun quaternionSerializerRejectsMissingComponents() {
        assertFailsWith<SerializationException> {
            json.decodeFromString(QuaterniondcSerializer, "[0.0,0.0,0.0]")
        }
    }

    @Test
    fun quaternionSerializerRejectsExtraComponents() {
        assertFailsWith<SerializationException> {
            json.decodeFromString(QuaternionfcSerializer, "[0.0,0.0,0.0,1.0,2.0]")
        }
    }
}
