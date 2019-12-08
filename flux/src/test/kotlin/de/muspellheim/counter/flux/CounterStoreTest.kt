/*
 * Architecture Study - Flux
 * Copyright (c) 2019 Falko Schumann
 */

package de.muspellheim.counter.flux

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

/** Unit tests. */
class CounterStoreTest {

    private lateinit var fixture: CounterStore

    private lateinit var dispatcher: Dispatcher<CounterAction>

    @BeforeEach
    fun setUp() {
        //  Given
        DispatchQueue.isTesting = true
        dispatcher = Dispatcher()
        fixture = CounterStore(dispatcher)
    }

    @Test
    fun `intial counter state`() {
        // Then
        assertEquals(0, fixture.value)
    }

    @Test
    fun `increment counter`() {
        // When
        dispatcher.dispatch(IncreaseCounterAction())
        dispatcher.dispatch(IncreaseCounterAction())

        // Then
        assertEquals(2, fixture.value)
    }

    @Test
    fun `decrement counter`() {
        //  Given
        dispatcher.dispatch(IncreaseCounterAction())
        dispatcher.dispatch(IncreaseCounterAction())

        // When
        dispatcher.dispatch(DecreaseCounterAction())

        // Then
        assertEquals(1, fixture.value)
    }
}
