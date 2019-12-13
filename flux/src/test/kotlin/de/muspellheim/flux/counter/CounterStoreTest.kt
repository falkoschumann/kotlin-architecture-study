/*
 * Architecture Study - Flux
 * Copyright (c) 2019 Falko Schumann
 */

package de.muspellheim.flux.counter

import de.muspellheim.flux.Dispatcher
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.util.concurrent.TimeUnit

/** Unit tests. */
class CounterStoreTest {

    private lateinit var fixture: CounterStore

    private lateinit var dispatcher: Dispatcher

    @BeforeEach
    fun setUp() {
        //
        //  Given
        //

        dispatcher = Dispatcher()
        fixture = CounterStore(dispatcher)
    }

    @Test
    fun `intial counter state`() {
        // Then
        assertEquals(0, fixture.value)
        assertFalse(fixture.isDecreasable)
    }

    @Test
    fun `increment counter`() {
        // Given
        dispatcher.dispatch(IncreaseCounterAction())
        TimeUnit.MILLISECONDS.sleep(1200)

        // When
        dispatcher.dispatch(IncreaseCounterAction())
        TimeUnit.MILLISECONDS.sleep(1200)

        // Then
        assertEquals(2, fixture.value)
        assertTrue(fixture.isDecreasable)
    }

    @Test
    fun `decrement counter`() {
        // Given
        dispatcher.dispatch(IncreaseCounterAction())
        TimeUnit.MILLISECONDS.sleep(1200)
        dispatcher.dispatch(IncreaseCounterAction())
        TimeUnit.MILLISECONDS.sleep(1200)

        // When
        dispatcher.dispatch(DecreaseCounterAction())
        TimeUnit.MILLISECONDS.sleep(1200)

        // Then
        assertEquals(1, fixture.value)
        assertTrue(fixture.isDecreasable)
    }

    @Test
    fun `counter should not be negative`() {
        // Given
        dispatcher.dispatch(IncreaseCounterAction())
        TimeUnit.MILLISECONDS.sleep(1200)
        dispatcher.dispatch(IncreaseCounterAction())
        TimeUnit.MILLISECONDS.sleep(1200)

        // When
        dispatcher.dispatch(DecreaseCounterAction())
        TimeUnit.MILLISECONDS.sleep(1200)
        dispatcher.dispatch(DecreaseCounterAction())
        TimeUnit.MILLISECONDS.sleep(1200)

        // Then
        assertEquals(0, fixture.value)
        assertFalse(fixture.isDecreasable)
    }

    @Test
    fun `counter can not be negative`() {
        // When
        dispatcher.dispatch(DecreaseCounterAction())
        TimeUnit.MILLISECONDS.sleep(1200)

        // Then
        assertEquals(0, fixture.value)
        assertFalse(fixture.isDecreasable)
    }
}
