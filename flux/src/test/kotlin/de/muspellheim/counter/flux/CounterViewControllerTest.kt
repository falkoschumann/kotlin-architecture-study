/*
 * Architecture Study - Flux
 * Copyright (c) 2019 Falko Schumann
 */

package de.muspellheim.counter.flux

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

/** Integration tests. */
class CounterViewControllerTest {

    private lateinit var fixture: CounterViewController

    @BeforeEach
    fun setUp() {
        // Given
        DispatchQueue.isTesting = true
        val dispatcher = Dispatcher<CounterAction>()
        val store = CounterStore(dispatcher)
        fixture = CounterViewController()
        fixture.injectCounterStore(store)
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
        assertEquals("1", fixture.value)
    }
}