/*
 * Architecture Study - Redux
 * Copyright (c) 2019 Falko Schumann
 */

package de.muspellheim.redux.counter

import de.muspellheim.redux.Store
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

/** Acceptance tests. */
class AcceptanceTests {

    @Test
    fun `intial counter state`() {
        // Given
        val fixture = createStore()

        // Then
        assertEquals(0, fixture.state.value)
        assertTrue(fixture.state.isDecreaseDisabled)
    }

    @Test
    fun `increment counter`() {
        // Given
        val fixture = Store(reducer, Counter(1, false))

        // When
        fixture.dispatch(IncreaseCounterAction())

        // Then
        assertEquals(2, fixture.state.value)
        assertFalse(fixture.state.isDecreaseDisabled)
    }

    @Test
    fun `decrement counter`() {
        // Given
        val fixture = Store(reducer, Counter(2, false))

        // When
        fixture.dispatch(DecreaseCounterAction())

        // Then
        assertEquals(1, fixture.state.value)
        assertFalse(fixture.state.isDecreaseDisabled)
    }

    @Test
    fun `counter can not be negative`() {
        // Given
        val fixture = Store(reducer, Counter(1, false))

        // When
        fixture.dispatch(DecreaseCounterAction())
        fixture.dispatch(DecreaseCounterAction())

        // Then
        assertEquals(0, fixture.state.value)
        assertTrue(fixture.state.isDecreaseDisabled)
    }
}
