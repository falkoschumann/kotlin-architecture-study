/*
 * Architecture Study - Actor Model
 * Copyright (c) 2019 Falko Schumann
 */

package de.muspellheim.actormodel.counter

import de.muspellheim.actormodel.EventBus
import de.muspellheim.actormodel.JavaFxActor
import de.muspellheim.actormodel.registerActor
import java.util.concurrent.TimeUnit
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

/** Acceptance tests. */
class AcceptanceTests {

    private lateinit var counterViewController: CounterViewController

    @BeforeEach
    fun setUp() {
        //
        // Given
        //

        JavaFxActor.isTesting = true

        val eventBus = EventBus("Acceptance Testing")
        val app = App(eventBus)
        app.init()

        counterViewController = CounterViewController()
        eventBus.registerActor(counterViewController)
    }

    @Test
    fun `intial counter state`() {
        // Then
        assertEquals("0", counterViewController.value)
        assertTrue(counterViewController.descreaseDisable)
    }

    @Test
    fun `increment counter`() {
        // When
        counterViewController.increase()
        counterViewController.increase()

        // Then
        TimeUnit.MILLISECONDS.sleep(500)
        assertEquals("2", counterViewController.value)
        assertFalse(counterViewController.descreaseDisable)
    }

    @Test
    fun `decrement counter`() {
        //  Given
        counterViewController.increase()
        counterViewController.increase()

        // When
        counterViewController.decrease()

        // Then
        TimeUnit.MILLISECONDS.sleep(500)
        assertEquals("1", counterViewController.value)
        assertFalse(counterViewController.descreaseDisable)
    }

    @Test
    fun `counter can not be negative`() {
        //  Given
        counterViewController.increase()
        counterViewController.increase()

        // When
        counterViewController.decrease()
        counterViewController.decrease()

        // Then
        TimeUnit.MILLISECONDS.sleep(500)
        assertEquals("0", counterViewController.value)
        assertTrue(counterViewController.descreaseDisable)
    }
}
