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
class CounterStoreTest {

    private lateinit var fixture: CounterStore

    @BeforeEach
    fun setUp() {
        //
        //  Given
        //

        val dispatcher = Dispatcher<Any>()
        fixture = CounterStore(dispatcher)
    }

    @Test
    fun `intial counter state`() {
        // Then
        assertEquals(Counter(value = 0, isDecreasable = false), fixture.state)
    }

    @Test
    fun `increment counter`() {
        // Given
        val oldState = Counter(value = 1, isDecreasable = true)

        // When
        val newState = fixture.reduce(oldState, IncreaseCounterAction())

        // Then
        assertEquals(Counter(value = 2, isDecreasable = true), newState)
    }

    @Test
    fun `decrement counter`() {
        // Given
        val oldState = Counter(value = 2, isDecreasable = true)

        // When
        val newState = fixture.reduce(oldState, DecreaseCounterAction())

        // Then
        assertEquals(Counter(value = 1, isDecreasable = true), newState)
    }

    @Test
    fun `counter should not be negative`() {
        // Given
        val oldState = Counter(value = 1, isDecreasable = true)

        // When
        val newState = fixture.reduce(oldState, DecreaseCounterAction())

        // Then
        assertEquals(Counter(value = 0, isDecreasable = false), newState)
    }

    @Test
    fun `counter can not be negative`() {
        // Given
        val oldState = Counter(value = 0, isDecreasable = false)

        // When
        val newState = fixture.reduce(oldState, DecreaseCounterAction())

        // Then
        assertEquals(Counter(value = 0, isDecreasable = false), newState)
    }
}
