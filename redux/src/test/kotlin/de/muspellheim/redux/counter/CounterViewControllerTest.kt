/*
 * Architecture Study - Redux
 * Copyright (c) 2019 Falko Schumann
 */

package de.muspellheim.redux.counter

import de.muspellheim.redux.Dispatcher
import de.muspellheim.shared.JavaFxExtension
import java.util.concurrent.TimeUnit
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Tag
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

/** Integration tests. */
@Tag("it")
@ExtendWith(JavaFxExtension::class)
class CounterViewControllerTest {

    private lateinit var fixture: CounterReduceViewController

    @BeforeEach
    fun setUp() {
        //
        // Given
        //

        val dispatcher = Dispatcher()

        val store = CounterReduceStore(dispatcher)
        val actions = CounterActions(dispatcher)
        fixture = CounterReduceViewController(store, actions)
    }

    @Test
    fun `intial counter state`() {
        // Then
        TimeUnit.MILLISECONDS.sleep(200)
        assertEquals("0", fixture.valueLabel.text)
        assertTrue(fixture.decreaseButton.isDisabled)
    }

    @Test
    fun `increment counter`() {
        // When
        fixture.increase()
        fixture.increase()

        // Then
        TimeUnit.SECONDS.sleep(3)
        assertEquals("2", fixture.valueLabel.text)
        assertFalse(fixture.decreaseButton.isDisabled)
    }

    @Test
    fun `decrement counter`() {
        //  Given
        fixture.increase()
        fixture.increase()

        // When
        fixture.decrease()

        // Then
        TimeUnit.SECONDS.sleep(4)
        assertEquals("1", fixture.valueLabel.text)
        assertFalse(fixture.decreaseButton.isDisabled)
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
        TimeUnit.SECONDS.sleep(5)
        assertEquals("0", fixture.valueLabel)
        assertTrue(fixture.decreaseButton.isDisabled)
    }
}