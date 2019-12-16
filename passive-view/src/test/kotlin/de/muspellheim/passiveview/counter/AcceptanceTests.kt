/*
 * Architecture Study - Passive View
 * Copyright (c) 2019 Falko Schumann
 */

package de.muspellheim.passiveview.counter

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

    private lateinit var counterControllerFixture: CounterController

    @BeforeEach
    fun setUp() {
        //
        // Given
        //

        val app = App()
        app.init()
        app.createRoot()
        counterControllerFixture = app.counterController
    }

    @Test
    fun `intial counter state`() {
        // Then
        assertEquals("0", counterControllerFixture.view.value)
        assertTrue(counterControllerFixture.view.isDescreaseDisabled)
    }

    @Test
    fun `increment counter`() {
        // When
        counterControllerFixture.view.onIncrease()
        counterControllerFixture.view.onIncrease()

        // Then
        Thread.sleep(200)
        await().atMost(Duration.ofSeconds(1)).until { counterControllerFixture.view.value == "2" }
        assertEquals("2", counterControllerFixture.view.value)
        assertFalse(counterControllerFixture.view.isDescreaseDisabled)
    }

    @Test
    fun `decrement counter`() {
        //  Given
        counterControllerFixture.view.onIncrease()
        counterControllerFixture.view.onIncrease()

        // When
        counterControllerFixture.view.onDecrease()

        // Then
        Thread.sleep(200)
        await().atMost(Duration.ofSeconds(1)).until { counterControllerFixture.view.value == "1" }
        assertEquals("1", counterControllerFixture.view.value)
        assertFalse(counterControllerFixture.view.isDescreaseDisabled)
    }

    @Test
    fun `counter can not be negative`() {
        //  Given
        counterControllerFixture.view.onIncrease()

        // When
        counterControllerFixture.view.onDecrease()
        counterControllerFixture.view.onDecrease()

        // Then
        Thread.sleep(200)
        await().atMost(Duration.ofSeconds(1)).until { counterControllerFixture.view.value == "0" }
        assertEquals("0", counterControllerFixture.view.value)
        assertTrue(counterControllerFixture.view.isDescreaseDisabled)
    }
}
