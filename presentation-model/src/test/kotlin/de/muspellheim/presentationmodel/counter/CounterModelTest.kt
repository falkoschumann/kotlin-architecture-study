/*
 * Architecture Study - Presentation Model
 * Copyright (c) 2019 Falko Schumann
 */

package de.muspellheim.presentationmodel.counter

import java.time.Duration
import org.awaitility.Awaitility.await
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

/** Integration tests. */
class CounterModelTest {

    private lateinit var fixture: CounterModel

    private var eventList = mutableListOf<String>()
    private val INCREASED = "increased"
    private val DECREASED = "decreased"

    @BeforeEach
    fun setUp() {
        // Given
        val model = CounterService()
        fixture = CounterModel(model)
        fixture.onIncreased += { eventList.add(INCREASED) }
        fixture.onDecreased += { eventList.add(DECREASED) }
    }

    @Test
    fun `intial counter state`() {
        // Then
        assertEquals("0", fixture.value)
        assertTrue(fixture.isDescreaseDisable)
        assertTrue(eventList.isEmpty())
    }

    @Test
    fun `increment counter`() {
        // When
        fixture.increase()
        fixture.increase()

        // Then
        await().atMost(Duration.ofSeconds(1)).until { eventList.size == 2 }
        assertEquals("2", fixture.value)
        assertFalse(fixture.isDescreaseDisable)
        assertEquals(listOf(INCREASED, INCREASED), eventList)
    }

    @Test
    fun `decrement counter`() {
        //  Given
        fixture.increase()
        fixture.increase()

        // When
        fixture.decrease()

        // Then
        await().atMost(Duration.ofSeconds(1)).until { eventList.size == 3 }
        assertEquals("1", fixture.value)
        assertFalse(fixture.isDescreaseDisable)
        assertEquals(listOf(INCREASED, INCREASED, DECREASED), eventList)
    }

    @Test
    fun `counter can not be negative`() {
        //  Given
        fixture.increase()

        // When
        fixture.decrease()
        fixture.decrease()

        // Then
        await().atMost(Duration.ofSeconds(1)).until { eventList.size == 3 }
        assertEquals("0", fixture.value)
        assertTrue(fixture.isDescreaseDisable)
        assertEquals(listOf(INCREASED, DECREASED, DECREASED), eventList)
    }
}
