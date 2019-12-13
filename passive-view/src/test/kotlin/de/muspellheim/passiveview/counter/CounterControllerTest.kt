/*
 * Architecture Study - Passive View
 * Copyright (c) 2019 Falko Schumann
 */

package de.muspellheim.passiveview.counter

import de.muspellheim.shared.Action
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
class CounterControllerTest {

    private lateinit var fixture: CounterController

    @BeforeEach
    fun setUp() {
        // Given
        val model = CounterService()
        val view = CounterViewStub()
        fixture = CounterController(model, view)
    }

    @Test
    fun `intial counter state`() {
        // Then
        assertEquals("0", fixture.view.value)
        assertTrue(fixture.view.isDescreaseDisable)
    }

    @Test
    fun `increment counter`() {
        // When
        fixture.view.onIncrease()
        fixture.view.onIncrease()

        // Then
        TimeUnit.SECONDS.sleep(3)
        assertEquals("2", fixture.view.value)
        assertFalse(fixture.view.isDescreaseDisable)
    }

    @Test
    fun `decrement counter`() {
        //  Given
        fixture.view.onIncrease()
        fixture.view.onIncrease()

        // When
        fixture.view.onDecrease()

        // Then
        TimeUnit.SECONDS.sleep(4)
        assertEquals("1", fixture.view.value)
        assertFalse(fixture.view.isDescreaseDisable)
    }

    @Test
    fun `counter should not be negative`() {
        //  Given
        fixture.view.onIncrease()
        fixture.view.onIncrease()

        // When
        fixture.view.onDecrease()
        fixture.view.onDecrease()

        // Then
        TimeUnit.SECONDS.sleep(5)
        assertEquals("0", fixture.view.value)
        assertTrue(fixture.view.isDescreaseDisable)
    }

    @Test
    fun `counter can not be negative`() {
        // When
        fixture.view.onDecrease()

        // Then
        TimeUnit.SECONDS.sleep(2)
        assertEquals("0", fixture.view.value)
        assertTrue(fixture.view.isDescreaseDisable)
    }
}

private class CounterViewStub : CounterView {

    override var value = ""
    override var isDescreaseDisable = false

    override val onIncrease = Action<Void>()
    override val onDecrease = Action<Void>()
}
