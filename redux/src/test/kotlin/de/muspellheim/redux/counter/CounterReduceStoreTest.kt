/*
 * Architecture Study - Redux
 * Copyright (c) 2019 Falko Schumann
 */

package de.muspellheim.redux.counter

import de.muspellheim.redux.Dispatcher
import java.util.concurrent.TimeUnit
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

/** Unit tests. */
class CounterReduceStoreTest {

    private lateinit var fixture: CounterReduceStore

    @BeforeEach
    fun setUp() {
        //
        //  Given
        //

        val dispatcher = Dispatcher()
        fixture = CounterReduceStore(dispatcher)
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
        TimeUnit.MILLISECONDS.sleep(1200)

        // Then
        assertEquals(Counter(value = 2, isDecreasable = true), newState)
    }

    @Test
    fun `decrement counter`() {
        // Given
        val oldState = Counter(value = 2, isDecreasable = true)

        // When
        val newState = fixture.reduce(oldState, DecreaseCounterAction())
        TimeUnit.MILLISECONDS.sleep(1200)

        // Then
        assertEquals(Counter(value = 1, isDecreasable = true), newState)
    }

    @Test
    fun `counter should not be negative`() {
        // Given
        val oldState = Counter(value = 1, isDecreasable = true)

        // When
        val newState = fixture.reduce(oldState, DecreaseCounterAction())
        TimeUnit.MILLISECONDS.sleep(1200)

        // Then
        assertEquals(Counter(value = 0, isDecreasable = false), newState)
    }

    @Test
    fun `counter can not be negative`() {
        // Given
        val oldState = Counter(value = 0, isDecreasable = false)

        // When
        val newState = fixture.reduce(oldState, DecreaseCounterAction())
        TimeUnit.MILLISECONDS.sleep(1200)

        // Then
        assertEquals(Counter(value = 0, isDecreasable = false), newState)
    }
}
