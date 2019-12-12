/*
 * Architecture Study - Presentation Model
 * Copyright (c) 2019 Falko Schumann
 */

package de.muspellheim.presentationmodel.counter

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Tag
import org.junit.jupiter.api.Test

/** Integration tests. */
class CounterModelTest {

    private lateinit var fixture: CounterModel

    private var increased = false
    private var decreased = false

    @BeforeEach
    fun setUp() {
        // Given
        val model = CounterService()
        fixture = CounterModel(model)
        fixture.onIncreased += { increased = true }
        fixture.onDecreased += { decreased = true }
    }

    @Test
    fun `intial counter state`() {
        // Then
        assertEquals("0", fixture.value)
        assertTrue(fixture.isDescreaseDisable)
        assertFalse(increased)
        assertFalse(decreased)
    }

    @Test
    fun `increment counter`() {
        // When
        fixture.increase()
        fixture.increase()

        // Then
        assertEquals("2", fixture.value)
        assertFalse(fixture.isDescreaseDisable)
        assertTrue(increased)
        assertFalse(decreased)
    }

    @Test
    fun `decrement counter`() {
        //  Given
        fixture.increase()
        fixture.increase()

        // When
        fixture.decrease()

        // Then
        assertEquals("1", fixture.value)
        assertFalse(fixture.isDescreaseDisable)
        assertTrue(increased)
        assertTrue(decreased)
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
        assertEquals("0", fixture.value)
        assertTrue(fixture.isDescreaseDisable)
        assertTrue(increased)
        assertTrue(decreased)
    }
}
