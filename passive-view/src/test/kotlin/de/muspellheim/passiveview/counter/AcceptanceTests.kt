/*
 * Architecture Study - Model View ViewModel
 * Copyright (c) 2019 Falko Schumann
 */

package de.muspellheim.passiveview.counter

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

    private lateinit var counterControllerFixture: CounterController

    @BeforeEach
    fun setUp() {
        //
        // Given
        //

        val app = App()
        app.init()
        counterControllerFixture = app.createRoot().second
    }

    @Test
    fun `intial counter state`() {
        // Then
        assertEquals("0", counterControllerFixture.view.value)
        assertTrue(counterControllerFixture.view.descreaseDisable)
    }

    @Test
    fun `increment counter`() {
        // When
        counterControllerFixture.view.onIncrease()
        counterControllerFixture.view.onIncrease()

        // Then
        TimeUnit.SECONDS.sleep(3)
        assertEquals("2", counterControllerFixture.view.value)
        assertFalse(counterControllerFixture.view.descreaseDisable)
    }

    @Test
    fun `decrement counter`() {
        //  Given
        counterControllerFixture.view.onIncrease()
        counterControllerFixture.view.onIncrease()

        // When
        counterControllerFixture.view.onDecrease()

        // Then
        TimeUnit.SECONDS.sleep(4)
        assertEquals("1", counterControllerFixture.view.value)
        assertFalse(counterControllerFixture.view.descreaseDisable)
    }

    @Test
    fun `counter can not be negative`() {
        //  Given
        counterControllerFixture.view.onIncrease()
        counterControllerFixture.view.onIncrease()

        // When
        counterControllerFixture.view.onDecrease()
        counterControllerFixture.view.onDecrease()

        // Then
        TimeUnit.SECONDS.sleep(5)
        assertEquals("0", counterControllerFixture.view.value)
        assertTrue(counterControllerFixture.view.descreaseDisable)
    }
}
