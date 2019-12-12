/*
 * Architecture Study - Presentation Model
 * Copyright (c) 2019 Falko Schumann
 */

package de.muspellheim.presentationmodel.counter

import java.util.concurrent.TimeUnit
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Tag
import org.junit.jupiter.api.Test

/** Integration tests. */
@Tag("it")
class CounterModelTest {

    private lateinit var fixture: CounterModel

    @BeforeEach
    fun setUp() {
        // Given
        val model = CounterService()
        fixture = CounterModel(model)
    }

    @Test
    fun `intial counter state`() {
        // Then
        assertEquals("0", fixture.value)
        assertTrue(fixture.isDescreaseDisable)
    }

    @Test
    fun `increment counter`() {
        // When
        fixture.increase()
        fixture.increase()

        // Then
        TimeUnit.SECONDS.sleep(3)
        assertEquals("2", fixture.value)
        assertFalse(fixture.isDescreaseDisable)
    }

    @Test
    fun `decrement counter`() {
        //  Given
        fixture.increase()
        fixture.increase()

        // When
        fixture.decrease()

        // Then
        TimeUnit.SECONDS.sleep(4)
        assertEquals("1", fixture.value)
        assertFalse(fixture.isDescreaseDisable)
    }

    @Test
    fun `counter can not be negative`() {
        //  Given
        fixture.increase()
        fixture.increase()

        // When
        fixture.decrease()
        fixture.decrease()

        // Then
        TimeUnit.SECONDS.sleep(5)
        assertEquals("0", fixture.value)
        assertTrue(fixture.isDescreaseDisable)
    }
}
