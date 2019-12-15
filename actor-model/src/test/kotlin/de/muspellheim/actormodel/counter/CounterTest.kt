/*
 * Architecture Study - Actor Model
 * Copyright (c) 2019 Falko Schumann
 */

package de.muspellheim.actormodel.counter

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

/** Unit tests. */
class CounterTest {

    private lateinit var fixture: Counter

    @BeforeEach
    fun setUp() {
        //  Given
        fixture = Counter()
    }

    @Test
    fun `initial state`() {
        // Then
        assertEquals(0, fixture.value)
        assertFalse(fixture.isDecreasable)
    }

    @Test
    fun `increment counter`() {
        // When
        fixture.increase()
        fixture.increase()

        // Then
        assertEquals(2, fixture.value)
        assertTrue(fixture.isDecreasable)
    }

    @Test
    fun `decrement counter`() {
        //  Given
        fixture.increase()
        fixture.increase()

        // When
        fixture.decrease()

        // Then
        assertEquals(1, fixture.value)
        assertTrue(fixture.isDecreasable)
    }

    @Test
    fun `counter can not be negative`() {
        //  Given
        fixture.increase()

        // When
        fixture.decrease()
        fixture.decrease()

        // Then
        assertEquals(0, fixture.value)
        assertFalse(fixture.isDecreasable)
    }
}
