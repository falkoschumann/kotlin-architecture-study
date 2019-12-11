/*
 * Architecture Study - Model View ViewModel
 * Copyright (c) 2019 Falko Schumann
 */

package de.muspellheim.mvvm.counter

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

    private lateinit var counterViewControllerFixture: CounterViewController

    @BeforeEach
    fun setUp() {
        //
        // Given
        //

        val app = App()
        app.init()

        counterViewControllerFixture = app.createRoot().second
    }

    @Test
    fun `intial counter state`() {
        // Then
        assertEquals("0", counterViewControllerFixture.value)
        assertTrue(counterViewControllerFixture.descreaseDisable)
    }

    @Test
    fun `increment counter`() {
        // When
        counterViewControllerFixture.increase()
        counterViewControllerFixture.increase()

        // Then
        TimeUnit.SECONDS.sleep(3)
        assertEquals("2", counterViewControllerFixture.value)
        assertFalse(counterViewControllerFixture.descreaseDisable)
    }

    @Test
    fun `decrement counter`() {
        //  Given
        counterViewControllerFixture.increase()
        counterViewControllerFixture.increase()

        // When
        counterViewControllerFixture.decrease()

        // Then
        TimeUnit.SECONDS.sleep(4)
        assertEquals("1", counterViewControllerFixture.value)
        assertFalse(counterViewControllerFixture.descreaseDisable)
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
        TimeUnit.SECONDS.sleep(5)
        assertEquals("0", counterViewControllerFixture.value)
        assertTrue(counterViewControllerFixture.descreaseDisable)
    }
}
