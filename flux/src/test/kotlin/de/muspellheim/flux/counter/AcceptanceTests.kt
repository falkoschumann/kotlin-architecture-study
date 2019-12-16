/*
 * Architecture Study - Flux
 * Copyright (c) 2019 Falko Schumann
 */

package de.muspellheim.flux.counter

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

    private lateinit var counterStore: CounterReduceStore
    private lateinit var counterActions: CounterActions
    private lateinit var actionList: List<Any>

    @BeforeEach
    fun setUp() {
        //
        // Given
        //

        actionList = emptyList()

        val app = App()
        app.init()
        app.createRoot()
        app.dispatcher.register { actionList = actionList + it }
        counterStore = app.injector.getInstance(CounterReduceStore::class.java)
        counterActions= app.injector.getInstance(CounterActions::class.java)
    }

    @Test
    fun `intial counter state`() {
        // Then
        assertEquals(0, counterStore.state.value)
        assertTrue(counterStore.state.isDecreaseDisabled)
    }

    @Test
    fun `increment counter`() {
        // When
        counterActions.increase()
        counterActions.increase()

        // Then
        await().atMost(Duration.ofSeconds(1)).until { actionList.size == 2 }
        assertEquals(2, counterStore.state.value)
        assertFalse(counterStore.state.isDecreaseDisabled)
    }

    @Test
    fun `decrement counter`() {
        //  Given
        counterActions.increase()
        counterActions.increase()

        // When
        counterActions.decrease()

        // Then
        await().atMost(Duration.ofSeconds(1)).until { actionList.size == 3 }
        assertEquals(1, counterStore.state.value)
        assertFalse(counterStore.state.isDecreaseDisabled)
    }

    @Test
    fun `counter can not be negative`() {
        //  Given
        counterActions.increase()

        // When
        counterActions.decrease()
        counterActions.decrease()

        // Then
        await().atMost(Duration.ofSeconds(1)).until { actionList.size == 3 }
        assertEquals(0, counterStore.state.value)
        assertTrue(counterStore.state.isDecreaseDisabled)
    }
}
