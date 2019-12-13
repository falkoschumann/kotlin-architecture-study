/*
 * Architecture Study - Redux
 * Copyright (c) 2019 Falko Schumann
 */

package de.muspellheim.redux.counter

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

/** Acceptance tests. */
class AcceptanceTests {

    private lateinit var fixture: App
    private lateinit var counterStore: CounterReduceStore
    private lateinit var counterActions: CounterActions
    @BeforeEach
    fun setUp() {
        //
        // Given
        //

        fixture = App()
        fixture.init()
        counterStore = fixture.injector.getInstance(CounterReduceStore::class.java)
        counterActions = fixture.injector.getInstance(CounterActions::class.java)
    }

    @Test
    fun `intial counter state`() {
        // Then
        assertEquals(0, counterStore.state.value)
        assertFalse(counterStore.state.isDecreasable)
    }

    @Test
    fun `increment counter`() {
        // When
        counterActions.increase()
        counterActions.increase()

        // Then
        assertEquals(2, counterStore.state.value)
        assertTrue(counterStore.state.isDecreasable)
    }

    @Test
    fun `decrement counter`() {
        //  Given
        counterActions.increase()
        counterActions.increase()

        // When
        counterActions.decrease()

        // Then
        assertEquals(1, counterStore.state.value)
        assertTrue(counterStore.state.isDecreasable)
    }

    @Test
    fun `counter can not be negative`() {
        //  Given
        counterActions.increase()
        counterActions.increase()

        // When
        counterActions.decrease()
        counterActions.decrease()

        // Then
        assertEquals(0, counterStore.state.value)
        assertFalse(counterStore.state.isDecreasable)
    }
}
