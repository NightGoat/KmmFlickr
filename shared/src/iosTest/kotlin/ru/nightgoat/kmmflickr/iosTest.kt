package ru.nightgoat.kmmflickr

import kotlinx.coroutines.runBlocking
import kotlin.test.Test
import kotlin.test.assertTrue

class IosGreetingTest {

    @Test
    fun testExample() {
        runBlocking {
            assertTrue(Greeting().greeting().contains("iOS"), "Check iOS is mentioned")
        }
    }
}