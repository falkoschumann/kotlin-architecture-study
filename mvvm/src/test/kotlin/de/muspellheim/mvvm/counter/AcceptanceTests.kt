/*
 * Architecture Study - Model View ViewModel
 * Copyright (c) 2019 Falko Schumann
 */

package de.muspellheim.mvvm.counter

import de.muspellheim.shared.JavaFxExtension
import java.time.Duration
import org.awaitility.Awaitility
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

    private lateinit var counterViewControllerFixture: CounterViewController

    @BeforeEach
    fun setUp() {
        //
        // Given
        //

        val app = App()
        app.init()
        app.createRoot()
        counterViewControllerFixture = app.counterViewController
    }

    @Test
    fun `intial counter state`() {
        // Then
        assertEquals("0", counterViewControllerFixture.value)
        assertTrue(counterViewControllerFixture.isDescreaseDisabled)
    }

    @Test
    fun `increment counter`() {
        // When
        counterViewControllerFixture.increase()
        counterViewControllerFixture.increase()

        // Then
        Thread.sleep(200)
        Awaitility.await().atMost(Duration.ofSeconds(1)).until { counterViewControllerFixture.value == "2" }
        assertEquals("2", counterViewControllerFixture.value)
        assertFalse(counterViewControllerFixture.isDescreaseDisabled)
    }

    @Test
    fun `decrement counter`() {
        //  Given
        counterViewControllerFixture.increase()
        counterViewControllerFixture.increase()

        // When
        counterViewControllerFixture.decrease()

        // Then
        Thread.sleep(200)
        Awaitility.await().atMost(Duration.ofSeconds(1)).until { counterViewControllerFixture.value == "1" }
        assertEquals("1", counterViewControllerFixture.value)
        assertFalse(counterViewControllerFixture.isDescreaseDisabled)
    }

    @Test
    fun `counter can not be negative`() {
        //  Given
        counterViewControllerFixture.increase()
        counterViewControllerFixture.increase()

        // When
        counterViewControllerFixture.decrease()
        counterViewControllerFixture.decrease()

        // Then
        Thread.sleep(200)
        Awaitility.await().atMost(Duration.ofSeconds(1)).until { counterViewControllerFixture.value == "0" }
        assertEquals("0", counterViewControllerFixture.value)
        assertTrue(counterViewControllerFixture.isDescreaseDisabled)
    }
}
