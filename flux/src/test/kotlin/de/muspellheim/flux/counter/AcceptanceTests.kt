/*
 * Architecture Study - Flux
 * Copyright (c) 2019 Falko Schumann
 */

package de.muspellheim.flux.counter

import de.muspellheim.shared.JavaFxExtension
import java.time.Duration
import org.awaitility.Awaitility.await
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
    private lateinit var actionList: List<Any>

    @BeforeEach
    fun setUp() {
        //
        // Given
        //

        actionList = emptyList()

        val app = App()
        app.init()
        app.createRoot()
        app.dispatcher.register { actionList = actionList + it }
        counterViewController = app.counterViewController
    }

    @Test
    fun `intial counter state`() {
        // Then
        assertEquals("0", counterViewController.valueLabel.text)
        assertTrue(counterViewController.decreaseButton.isDisabled)
    }

    @Test
    fun `increment counter`() {
        // When
        counterViewController.increase()
        counterViewController.increase()

        // Then
        await().atMost(Duration.ofSeconds(1)).until { actionList.size == 2 }
        assertEquals("2", counterViewController.valueLabel.text)
        assertFalse(counterViewController.decreaseButton.isDisabled)
    }

    @Test
    fun `decrement counter`() {
        //  Given
        counterViewController.increase()
        counterViewController.increase()

        // When
        counterViewController.decrease()

        // Then
        await().atMost(Duration.ofSeconds(1)).until { actionList.size == 3 }
        assertEquals("1", counterViewController.valueLabel.text)
        assertFalse(counterViewController.decreaseButton.isDisabled)
    }

    @Test
    fun `counter can not be negative`() {
        //  Given
        counterViewController.increase()

        // When
        counterViewController.decrease()
        counterViewController.decrease()

        // Then
        await().atMost(Duration.ofSeconds(1)).until { actionList.size == 3 }
        assertEquals("0", counterViewController.valueLabel.text)
        assertTrue(counterViewController.decreaseButton.isDisabled)
    }
}
