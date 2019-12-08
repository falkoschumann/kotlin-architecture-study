/*
 * Architecture Study - Model View ViewModel
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

    private lateinit var counterViewControllerFixture: CounterViewController

    @BeforeEach
    fun setUp() {
        //
        // Given
        //

        DispatchQueue.isTesting = true

        val app = App()
        app.init()

        counterViewControllerFixture = CounterViewController()
        counterViewControllerFixture.injectCounterService(app.counterService)
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
        assertEquals("0", counterViewControllerFixture.value)
        assertTrue(counterViewControllerFixture.descreaseDisable)
    }
}
