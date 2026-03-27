package net.azisaba.serialization

import kotlinx.serialization.json.Json
import kotlin.random.Random
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertTrue

class ValueProvidersTest {
    private val json = Json

    @Test
    fun intProviderConstantRoundTrips() {
        val provider: IntProvider = IntProvider.Constant(12)

        val encoded = json.encodeToString(provider)
        val decoded = json.decodeFromString<IntProvider>(encoded)

        assertEquals(provider, decoded)
        assertEquals(12, decoded.sample(Random(0)))
    }

    @Test
    fun intProviderUniformSamplesInclusiveBounds() {
        val provider = IntProvider.Uniform(2, 5)
        repeat(256) {
            val value = provider.sample(Random(it))
            assertTrue(value in 2..5, "sampled value $value was outside [2, 5]")
        }
    }

    @Test
    fun intProviderBiasedToBottomSamplesWithinRange() {
        val provider = IntProvider.BiasedToBottom(3, 9)
        repeat(256) {
            val value = provider.sample(Random(it))
            assertTrue(value in 3..9, "sampled value $value was outside [3, 9]")
        }
    }

    @Test
    fun intProviderClampedClampsSourceValue() {
        val provider: IntProvider = IntProvider.Clamped(IntProvider.Constant(99), 1, 5)

        val encoded = json.encodeToString(provider)
        val decoded = json.decodeFromString<IntProvider>(encoded)

        assertEquals(provider, decoded)
        assertEquals(5, decoded.sample(Random(0)))
    }

    @Test
    fun intProviderClampedNormalSamplesWithinRange() {
        val provider = IntProvider.ClampedNormal(mean = 10f, deviation = 20f, minInclusive = 4, maxInclusive = 8)
        repeat(256) {
            val value = provider.sample(Random(it))
            assertTrue(value in 4..8, "sampled value $value was outside [4, 8]")
        }
    }

    @Test
    fun intProviderWeightedListRoundTripsAndSamplesEntries() {
        val provider: IntProvider = IntProvider.WeightedList(
            listOf(
                IntProvider.WeightedList.Entry(IntProvider.Constant(4), 1),
                IntProvider.WeightedList.Entry(IntProvider.Constant(7), 3),
            ),
        )

        val encoded = json.encodeToString(provider)
        val decoded = json.decodeFromString<IntProvider>(encoded)

        assertEquals(provider, decoded)
        repeat(256) {
            val value = decoded.sample(Random(it))
            assertTrue(value == 4 || value == 7, "sampled unexpected value $value")
        }
    }

    @Test
    fun intProvidersRejectInvalidArguments() {
        assertFailsWith<IllegalArgumentException> { IntProvider.Uniform(5, 4) }
        assertFailsWith<IllegalArgumentException> { IntProvider.BiasedToBottom(5, 4) }
        assertFailsWith<IllegalArgumentException> { IntProvider.Clamped(IntProvider.Constant(0), 5, 4) }
        assertFailsWith<IllegalArgumentException> { IntProvider.ClampedNormal(0f, 1f, 5, 4) }
        assertFailsWith<IllegalArgumentException> { IntProvider.WeightedList(emptyList()) }
        assertFailsWith<IllegalArgumentException> {
            IntProvider.WeightedList(listOf(IntProvider.WeightedList.Entry(IntProvider.Constant(1), 0)))
        }
    }

    @Test
    fun floatProviderConstantRoundTrips() {
        val provider: FloatProvider = FloatProvider.Constant(1.5f)

        val encoded = json.encodeToString(provider)
        val decoded = json.decodeFromString<FloatProvider>(encoded)

        assertEquals(provider, decoded)
        assertEquals(1.5f, decoded.sample(Random(0)))
    }

    @Test
    fun floatProviderUniformSamplesHalfOpenRange() {
        val provider = FloatProvider.Uniform(2.5f, 4.5f)
        repeat(256) {
            val value = provider.sample(Random(it))
            assertTrue(value >= 2.5f, "sampled value $value was below 2.5")
            assertTrue(value < 4.5f, "sampled value $value was not below 4.5")
        }
    }

    @Test
    fun floatProviderClampedNormalSamplesWithinRange() {
        val provider = FloatProvider.ClampedNormal(mean = 3f, deviation = 20f, min = 1.25f, max = 2.75f)
        repeat(256) {
            val value = provider.sample(Random(it))
            assertTrue(value in 1.25f..2.75f, "sampled value $value was outside [1.25, 2.75]")
        }
    }

    @Test
    fun floatProviderTrapezoidSamplesWithinRange() {
        val provider = FloatProvider.Trapezoid(min = 2f, max = 6f, plateau = 1.5f)
        repeat(256) {
            val value = provider.sample(Random(it))
            assertTrue(value in 2f..6f, "sampled value $value was outside [2, 6]")
        }
    }

    @Test
    fun floatProviderMultipliedRoundTripsAndMultipliesValues() {
        val provider: FloatProvider = FloatProvider.Multiplied(
            listOf(
                FloatProvider.Constant(1.5f),
                FloatProvider.Constant(2f),
                FloatProvider.Constant(4f),
            ),
        )

        val encoded = json.encodeToString(provider)
        val decoded = json.decodeFromString<FloatProvider>(encoded)

        assertEquals(provider, decoded)
        assertEquals(12f, decoded.sample(Random(0)))
    }

    @Test
    fun floatProvidersRejectInvalidArguments() {
        assertFailsWith<IllegalArgumentException> { FloatProvider.Uniform(3f, 3f) }
        assertFailsWith<IllegalArgumentException> { FloatProvider.ClampedNormal(0f, 1f, 5f, 4f) }
        assertFailsWith<IllegalArgumentException> { FloatProvider.Trapezoid(5f, 4f, 0f) }
        assertFailsWith<IllegalArgumentException> { FloatProvider.Trapezoid(1f, 3f, 3f) }
        assertFailsWith<IllegalArgumentException> { FloatProvider.Multiplied(emptyList()) }
    }
}
