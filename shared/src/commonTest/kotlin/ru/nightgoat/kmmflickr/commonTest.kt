package ru.nightgoat.kmmflickr

import kotlinx.coroutines.runBlocking
import kotlin.test.Test
import kotlin.test.assertTrue

class CommonGreetingTest {

    @Test
    fun testExample() {
        runBlocking {
            assertTrue(Greeting().greeting().contains("Hello"), "Check 'Hello' is mentioned")
        }
    }
}