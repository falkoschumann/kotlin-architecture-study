/*
 * Architecture Study - Actor Model
 * Copyright (c) 2019 Falko Schumann
 */

package de.muspellheim.actormodel.counter

import de.muspellheim.actormodel.EventBus
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

    private lateinit var counterViewActor: CounterViewActor
    private lateinit var eventList: List<Any>

    @BeforeEach
    fun setUp() {
        //
        // Given
        //

        eventList = emptyList()

        val app = App(EventBus("Testing Event Bus"))
        app.eventBus.subscribe {
            eventList = eventList + it
        }
        app.init()
        app.createRoot()
        counterViewActor = app.counterViewActor
    }

    @Test
    fun `intial counter view state`() {
        // Then
        assertEquals("0", counterViewActor.valueLabel.text)
        assertTrue(counterViewActor.decreaseButton.isDisabled)
    }

    @Test
    fun `increment counter`() {
        // When
        counterViewActor.increase()
        counterViewActor.increase()

        // Then
        await().atMost(Duration.ofSeconds(10)).until { eventList.size == 4 }
        assertEquals("2", counterViewActor.valueLabel.text)
        assertFalse(counterViewActor.decreaseButton.isDisabled)
    }

    @Test
    fun `decrement counter`() {
        //  Given
        counterViewActor.increase()
        counterViewActor.increase()

        // When
        counterViewActor.decrease()

        // Then
        await().atMost(Duration.ofSeconds(10)).until { eventList.size == 6 }
        assertEquals("1", counterViewActor.valueLabel.text)
        assertFalse(counterViewActor.decreaseButton.isDisabled)
    }

    @Test
    fun `counter can not be negative`() {
        //  Given
        counterViewActor.increase()
        counterViewActor.increase()

        // When
        counterViewActor.decrease()
        counterViewActor.decrease()

        // Then
        await().atMost(Duration.ofSeconds(10)).until { eventList.size == 8 }
        assertEquals("0", counterViewActor.valueLabel.text)
        assertTrue(counterViewActor.decreaseButton.isDisabled)
    }
}
