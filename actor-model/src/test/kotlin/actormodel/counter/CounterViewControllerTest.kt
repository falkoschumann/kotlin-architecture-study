/*
 * Architecture Study - Actor Model
 * Copyright (c) 2019 Falko Schumann
 */

package de.muspellheim.actormodel.counter

import de.muspellheim.actormodel.JavaFxActor
import java.util.concurrent.TimeUnit
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

/** Integration tests. */
class CounterViewControllerTest {

    private lateinit var fixture: CounterViewController

    @BeforeEach
    fun setUp() {
        //
        // Given
        //

        JavaFxActor.isTesting = true

        fixture = CounterViewController()
        val counter = Counter()
        val counterActor = CounterActor(counter)

        fixture.outbox += { counterActor.receive(it!!) }
        counterActor.outbox += { fixture.receive(it!!) }
    }

    @Test
    fun `intial counter state`() {
        // Then
        assertEquals("0", fixture.value)
        assertTrue(fixture.descreaseDisable)
    }

    @Test
    fun `increment counter`() {
        // When
        fixture.increase()
        fixture.increase()

        // Then
        TimeUnit.MILLISECONDS.sleep(500)
        assertEquals("2", fixture.value)
        assertFalse(fixture.descreaseDisable)
    }

    @Test
    fun `decrement counter`() {
        //  Given
        fixture.increase()
        fixture.increase()

        // When
        fixture.decrease()

        // Then
        TimeUnit.MILLISECONDS.sleep(500)
        assertEquals("1", fixture.value)
        assertFalse(fixture.descreaseDisable)
    }

    @Test
    fun `counter can not be negative`() {
        //  Given
        fixture.increase()
        fixture.increase()

        // When
        fixture.decrease()
        fixture.decrease()

        // Then
        TimeUnit.MILLISECONDS.sleep(500)
        assertEquals("0", fixture.value)
        assertTrue(fixture.descreaseDisable)
    }
}
