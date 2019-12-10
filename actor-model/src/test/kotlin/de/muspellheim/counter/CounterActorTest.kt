/*
 * Architecture Study - Actor Model
 * Copyright (c) 2019 Falko Schumann
 */

package de.muspellheim.counter

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

/** Unit tests. */
class CounterActorTest {

    private lateinit var fixture: CounterActor

    private var event: Any? = null

    @BeforeEach
    fun setUp() {
        //  Given
        fixture = CounterActor()
        fixture.outbox.addHandler { event = it }
    }

    @Test
    fun `increment counter`() {
        // When
        fixture.work(IncreaseCounterAction())
        fixture.work(IncreaseCounterAction())

        // Then
        assertEquals(2, (event as CounterUpdatedEvent).newValue)
    }

    @Test
    fun `decrement counter`() {
        //  Given
        fixture.work(IncreaseCounterAction())
        fixture.work(IncreaseCounterAction())

        // When
        fixture.work(DecreaseCounterAction())

        // Then
        assertEquals(1, (event as CounterUpdatedEvent).newValue)
    }
}
