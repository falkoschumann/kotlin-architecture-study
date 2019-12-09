/*
 * Architecture Study - Flux
 * Copyright (c) 2019 Falko Schumann
 */

package de.muspellheim.counter

import de.muspellheim.shared.DispatchQueue
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

/** Acceptance tests. */
class AcceptanceTests {

    private lateinit var fixture: App

    @BeforeEach
    fun setUp() {
        //
        // Given
        //

        DispatchQueue.isTesting = true

        fixture = App()
        fixture.init()
    }

    @Test
    fun `intial counter state`() {
        // Then
        assertEquals(0, fixture.counterStore.state.value)
        assertFalse(fixture.counterStore.state.isDecreasable)
    }

    @Test
    fun `increment counter`() {
        // When
        fixture.counterActions.increase()
        fixture.counterActions.increase()

        // Then
        assertEquals(2, fixture.counterStore.state.value)
        assertTrue(fixture.counterStore.state.isDecreasable)
    }

    @Test
    fun `decrement counter`() {
        //  Given
        fixture.counterActions.increase()
        fixture.counterActions.increase()

        // When
        fixture.counterActions.decrease()

        // Then
        assertEquals(1, fixture.counterStore.state.value)
        assertTrue(fixture.counterStore.state.isDecreasable)
    }

    @Test
    fun `counter can not be negative`() {
        //  Given
        fixture.counterActions.increase()
        fixture.counterActions.increase()

        // When
        fixture.counterActions.decrease()
        fixture.counterActions.decrease()

        // Then
        assertEquals(0, fixture.counterStore.state.value)
        assertFalse(fixture.counterStore.state.isDecreasable)
    }
}
