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
    private lateinit var counterViewController: CounterViewController

    @BeforeEach
    fun setUp() {
        //
        // Given
        //

        DispatchQueue.isTesting = true

        fixture = App()
        fixture.init()

        counterViewController = CounterViewController()
        counterViewController.injectCounterStore(fixture.counterStore)
    }

    @Test
    fun `intial counter state`() {
        // Then
        assertEquals(0, fixture.counterStore.value)
        assertFalse(fixture.counterStore.decreaseable)
        assertEquals("0", counterViewController.value)
        assertTrue(counterViewController.descreaseDisable)
    }

    @Test
    fun `increment counter`() {
        // When
        fixture.dispatcher.dispatch(IncreaseCounterAction())
        fixture.dispatcher.dispatch(IncreaseCounterAction())

        // Then
        assertEquals(2, fixture.counterStore.value)
        assertTrue(fixture.counterStore.decreaseable)
        assertEquals("2", counterViewController.value)
        assertFalse(counterViewController.descreaseDisable)
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
        assertTrue(fixture.counterStore.decreaseable)
        assertEquals("1", counterViewController.value)
        assertFalse(counterViewController.descreaseDisable)
    }

    @Test
    fun `counter can not be negative`() {
        //  Given
        fixture.dispatcher.dispatch(IncreaseCounterAction())
        fixture.dispatcher.dispatch(IncreaseCounterAction())

        // When
        fixture.dispatcher.dispatch(DecreaseCounterAction())
        fixture.dispatcher.dispatch(DecreaseCounterAction())

        // Then
        assertEquals(0, fixture.counterStore.value)
        assertFalse(fixture.counterStore.decreaseable)
        assertEquals("0", counterViewController.value)
        assertTrue(counterViewController.descreaseDisable)
    }
}
