/*
 * Architecture Study - Flux
 * Copyright (c) 2019 Falko Schumann
 */

package de.muspellheim.flux.counter

import de.muspellheim.flux.Dispatcher
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

/** Unit tests. */
class CounterStoreTest {

    private lateinit var fixture: CounterStore

    private lateinit var dispatcher: Dispatcher

    @BeforeEach
    fun setUp() {
        //  Given
        dispatcher = Dispatcher()
        fixture = CounterStore(dispatcher)
    }

    @Test
    fun `intial counter state`() {
        // Then
        assertEquals(0, fixture.value)
        assertTrue(fixture.isDecreaseDisabled)
    }

    @Test
    fun `increment counter`() {
        // When
        dispatcher.dispatch(IncreaseCounterAction())
        dispatcher.dispatch(IncreaseCounterAction())

        // Then
        assertEquals(2, fixture.value)
        assertFalse(fixture.isDecreaseDisabled)
    }

    @Test
    fun `decrement counter`() {
        // Given
        dispatcher.dispatch(IncreaseCounterAction())
        dispatcher.dispatch(IncreaseCounterAction())

        // When
        dispatcher.dispatch(DecreaseCounterAction())

        // Then
        assertEquals(1, fixture.value)
        assertFalse(fixture.isDecreaseDisabled)
    }

    @Test
    fun `counter can not be negative`() {
        // Given
        dispatcher.dispatch(IncreaseCounterAction())

        // When
        dispatcher.dispatch(DecreaseCounterAction())
        dispatcher.dispatch(DecreaseCounterAction())

        // Then
        assertEquals(0, fixture.value)
        assertTrue(fixture.isDecreaseDisabled)
    }
}
