/*
 * Architecture Study - Event Bus
 * Copyright (c) 2019 Falko Schumann
 */

package de.muspellheim.counter.eventbus

import java.util.concurrent.TimeUnit
import org.junit.jupiter.api.Assertions.assertEquals
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
        val counterActor = CounterActor()

        fixture.outbox.addHandler { counterActor.receive(it) }
        counterActor.outbox.addHandler { fixture.receive(it) }
    }

    @Test
    fun `intial counter state`() {
        // Then
        assertEquals("0", fixture.value)
    }

    @Test
    fun `increment counter`() {
        // When
        fixture.increase()
        fixture.increase()

        // Then
        TimeUnit.MILLISECONDS.sleep(500)
        assertEquals("2", fixture.value)
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
    }
}
