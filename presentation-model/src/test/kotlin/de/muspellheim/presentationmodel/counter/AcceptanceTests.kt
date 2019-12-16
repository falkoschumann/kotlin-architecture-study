/*
 * Architecture Study - Presentation Model
 * Copyright (c) 2019 Falko Schumann
 */

package de.muspellheim.presentationmodel.counter

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

/** Acceptance tests. */
class AcceptanceTests {

    private lateinit var counterModelFixture: CounterModel

    private var eventList = mutableListOf<String>()
    private val INCREASED = "increased"
    private val DECREASED = "decreased"

    @BeforeEach
    fun setUp() {
        //
        // Given
        //

        val app = App()
        app.init()
        counterModelFixture = app.injector.getInstance(CounterModel::class.java)
        counterModelFixture.onIncreased += { eventList.add(INCREASED) }
        counterModelFixture.onDecreased += { eventList.add(DECREASED) }
    }

    @Test
    fun `intial counter state`() {
        // Then
        assertEquals("0", counterModelFixture.value)
        assertTrue(counterModelFixture.isDescreaseDisable)
        assertTrue(eventList.isEmpty())
    }

    @Test
    fun `increment counter`() {
        // When
        counterModelFixture.increase()
        counterModelFixture.increase()

        // Then
        assertEquals("2", counterModelFixture.value)
        assertFalse(counterModelFixture.isDescreaseDisable)
        assertEquals(listOf(INCREASED, INCREASED), eventList)
    }

    @Test
    fun `decrement counter`() {
        //  Given
        counterModelFixture.increase()
        counterModelFixture.increase()

        // When
        counterModelFixture.decrease()

        // Then
        assertEquals("1", counterModelFixture.value)
        assertFalse(counterModelFixture.isDescreaseDisable)
        assertEquals(listOf(INCREASED, INCREASED, DECREASED), eventList)
    }

    @Test
    fun `counter can not be negative`() {
        //  Given
        counterModelFixture.increase()

        // When
        counterModelFixture.decrease()
        counterModelFixture.decrease()

        // Then
        assertEquals("0", counterModelFixture.value)
        assertTrue(counterModelFixture.isDescreaseDisable)
        assertEquals(listOf(INCREASED, DECREASED, DECREASED), eventList)
    }
}
