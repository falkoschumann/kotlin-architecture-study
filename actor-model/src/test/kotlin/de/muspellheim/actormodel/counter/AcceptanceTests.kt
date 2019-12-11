/*
 * Architecture Study - Actor Model
 * Copyright (c) 2019 Falko Schumann
 */

package de.muspellheim.actormodel.counter

import de.muspellheim.actormodel.EventBus
import de.muspellheim.shared.JavaFxExtension
import java.util.concurrent.TimeUnit
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Tag
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

/** Acceptance tests. */
@Tag("it")
@ExtendWith(JavaFxExtension::class)
class AcceptanceTests {

    private lateinit var counterViewController: CounterViewController

    @BeforeEach
    fun setUp() {
        //
        // Given
        //

        val eventBus = EventBus("Acceptance Testing")
        val app = App(eventBus)
        app.init()
        counterViewController = app.createRoot().second
    }

    @Test
    fun `intial counter view state`() {
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
        TimeUnit.SECONDS.sleep(3)
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
        TimeUnit.SECONDS.sleep(4)
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
        TimeUnit.SECONDS.sleep(5)
        assertEquals("0", counterViewController.value)
        assertTrue(counterViewController.descreaseDisable)
    }
}
