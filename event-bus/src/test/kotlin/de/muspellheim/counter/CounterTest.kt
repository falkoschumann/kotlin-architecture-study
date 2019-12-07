/*
 * Architecture Study - Event Bus
 * Copyright (c) 2019 Falko Schumann
 */

package de.muspellheim.counter

import org.junit.jupiter.api.Assertions.assertEquals
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
    fun `intial counter state`() {
        // Then
        assertEquals(0, fixture.value)
    }

    @Test
    fun `increment counter`() {
        // When
        fixture.work(IncreaseCounterAction())
        val event = fixture.work(IncreaseCounterAction())

        // Then
        assertEquals(2, (event as CounterUpdatedEvent).newValue)
    }

    @Test
    fun `decrement counter`() {
        //  Given
        fixture.work(IncreaseCounterAction())
        fixture.work(IncreaseCounterAction())

        // When
        val event = fixture.work(DecreaseCounterAction())

        // Then
        assertEquals(1, (event as CounterUpdatedEvent).newValue)
    }
}
