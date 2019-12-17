/*
 * Architecture Study - Flux
 * Copyright (c) 2019 Falko Schumann
 */

package de.muspellheim.flux.counter

import de.muspellheim.flux.Dispatcher
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

/** Unit tests. */
class CounterReduceStoreTest {

    private lateinit var fixture: CounterReduceStore

    @BeforeEach
    fun setUp() {
        //  Given
        val dispatcher = Dispatcher()
        fixture = CounterReduceStore(dispatcher)
    }

    @Test
    fun `intial counter state`() {
        // Then
        assertEquals(Counter(0, true), fixture.state)
    }

    @Test
    fun `increment counter`() {
        // Given
        val oldState = Counter(1, false)

        // When
        val newState = fixture.reduce(oldState, IncreaseCounterAction())

        // Then
        assertEquals(Counter(2, false), newState)
    }

    @Test
    fun `decrement counter`() {
        // Given
        val oldState = Counter(2, false)

        // When
        val newState = fixture.reduce(oldState, DecreaseCounterAction())

        // Then
        assertEquals(Counter(1, false), newState)
    }

    @Test
    fun `counter can not be negative`() {
        // Given
        val oldState = Counter(0, true)

        // When
        val newState = fixture.reduce(oldState, DecreaseCounterAction())

        // Then
        assertEquals(Counter(0, true), newState)
    }
}
