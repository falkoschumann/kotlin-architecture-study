/*
 * Architecture Study - Presentation Model
 * Copyright (c) 2019 Falko Schumann
 */

package de.muspellheim.presentationmodel.counter

import de.muspellheim.shared.JavaFxExtension
import java.util.concurrent.TimeUnit
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

    private lateinit var counterModelFixture: CounterModel

    @BeforeEach
    fun setUp() {
        //
        // Given
        //

        val app = App()
        app.init()
        counterModelFixture = app.injector.getInstance(CounterModel::class.java)
    }

    @Test
    fun `intial counter state`() {
        // Then
        assertEquals("0", counterModelFixture.value)
        assertTrue(counterModelFixture.isDescreaseDisable)
    }

    @Test
    fun `increment counter`() {
        // When
        counterModelFixture.increase()
        counterModelFixture.increase()

        // Then
        TimeUnit.SECONDS.sleep(3)
        assertEquals("2", counterModelFixture.value)
        assertFalse(counterModelFixture.isDescreaseDisable)
    }

    @Test
    fun `decrement counter`() {
        //  Given
        counterModelFixture.increase()
        counterModelFixture.increase()

        // When
        counterModelFixture.decrease()

        // Then
        TimeUnit.SECONDS.sleep(4)
        assertEquals("1", counterModelFixture.value)
        assertFalse(counterModelFixture.isDescreaseDisable)
    }

    @Test
    fun `counter can not be negative`() {
        //  Given
        counterModelFixture.increase()
        counterModelFixture.increase()

        // When
        counterModelFixture.decrease()
        counterModelFixture.decrease()

        // Then
        TimeUnit.SECONDS.sleep(5)
        assertEquals("0", counterModelFixture.value)
        assertTrue(counterModelFixture.isDescreaseDisable)
    }
}
