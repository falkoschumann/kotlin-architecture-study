/*
 * Architecture Study - Flux
 * Copyright (c) 2019 Falko Schumann
 */

package de.muspellheim.counter.flux

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

/** Acceptance tests. */
class AcceptanceTests {

    private lateinit var fixture: App

    @BeforeEach
    fun setUp() {
        // Given
        DispatchQueue.isTesting = true
        fixture = App()
        fixture.init()
    }

    @Test
    fun `intial counter state`() {
        // Then
        assertEquals(0, fixture.counterStore.value)
    }

    @Test
    fun `increment counter`() {
        // When
        fixture.dispatcher.dispatch(IncreaseCounterAction())
        fixture.dispatcher.dispatch(IncreaseCounterAction())

        // Then
        assertEquals(2, fixture.counterStore.value)
    }

    @Test
    fun `decrement counter`() {
        //  Given
        fixture.dispatcher.dispatch(IncreaseCounterAction())
        fixture.dispatcher.dispatch(IncreaseCounterAction())

        // When
        fixture.dispatcher.dispatch(DecreaseCounterAction())

        // Then
        assertEquals(1, fixture.counterStore.value)
    }
}