/*
 * Architecture Study - Model View ViewModel
 * Copyright (c) 2019 Falko Schumann
 */

package de.muspellheim.mvvm.counter

import de.muspellheim.shared.JavaFxExtension
import org.awaitility.Awaitility
import java.util.concurrent.TimeUnit
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Tag
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import java.time.Duration

/** Integration tests. */
@Tag("it")
@ExtendWith(JavaFxExtension::class)
class CounterViewControllerTest {

    private lateinit var fixture: CounterViewController

    @BeforeEach
    fun setUp() {
        // Given
        val service = CounterService()
        fixture = CounterViewController(service)
    }

    @Test
    fun `intial counter state`() {
        // Then
        assertEquals("0", fixture.value)
        assertTrue(fixture.isDescreaseDisabled)
    }

    @Test
    fun `increment counter`() {
        // When
        fixture.increase()
        fixture.increase()

        // Then
        Thread.sleep(200)
        Awaitility.await().atMost(Duration.ofSeconds(1)).until { fixture.value == "2" }
        assertEquals("2", fixture.value)
        assertFalse(fixture.isDescreaseDisabled)
    }

    @Test
    fun `decrement counter`() {
        //  Given
        fixture.increase()
        fixture.increase()

        // When
        fixture.decrease()

        // Then
        Thread.sleep(200)
        Awaitility.await().atMost(Duration.ofSeconds(1)).until { fixture.value == "1" }
        assertEquals("1", fixture.value)
        assertFalse(fixture.isDescreaseDisabled)
    }

    @Test
    fun `counter can not be negative`() {
        //  Given
        fixture.increase()

        // When
        fixture.decrease()
        fixture.decrease()

        // Then
        Thread.sleep(200)
        Awaitility.await().atMost(Duration.ofSeconds(1)).until { fixture.value == "0" }
        assertEquals("0", fixture.value)
        assertTrue(fixture.isDescreaseDisabled)
    }
}
