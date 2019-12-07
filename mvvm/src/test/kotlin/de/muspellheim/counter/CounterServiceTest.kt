/*
 * Architecture Study - Model View ViewModel
 * Copyright (c) 2019 Falko Schumann
 */

package de.muspellheim.counter

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
        fixture.increment()
        fixture.increment()

        // Then
        assertEquals(2, fixture.value)
    }

    @Test
    fun `decrement counter`() {
        //  Given
        fixture.increment()
        fixture.increment()

        // When
        fixture.decrement()

        // Then
        assertEquals(1, fixture.value)
    }
}
