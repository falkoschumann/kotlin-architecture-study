/*
 * Architecture Study - Passive View
 * Copyright (c) 2019 Falko Schumann
 */

package de.muspellheim.passiveview.counter

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

/** Unit tests. */
class CounterServiceTest {

    private lateinit var fixture: CounterService

    @BeforeEach
    fun setUp() {
        //  Given
        fixture = CounterService()
    }

    @Test
    fun `intial counter state`() {
        // Then
        assertEquals(0, fixture.value)
    }

    @Test
    fun `increment counter`() {
        // When
        fixture.increase()
        fixture.increase()

        // Then
        assertEquals(2, fixture.value)
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
    }
}
