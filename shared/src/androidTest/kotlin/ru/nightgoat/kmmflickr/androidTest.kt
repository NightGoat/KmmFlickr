package ru.nightgoat.kmmflickr

import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertTrue
import org.junit.Test

class AndroidGreetingTest {

    @Test
    fun testExample() {
        runBlocking {
            assertTrue("Check Android is mentioned", Greeting().greeting().contains("Android"))
        }
    }
}