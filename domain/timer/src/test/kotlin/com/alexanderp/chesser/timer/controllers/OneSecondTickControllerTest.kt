package com.alexanderp.chesser.timer.controllers

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.cancelAndJoin
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.time.Duration.Companion.seconds
import kotlin.time.ExperimentalTime

@ExperimentalTime
@OptIn(ExperimentalCoroutinesApi::class)
internal class OneSecondTickControllerTest {
    private val testCoroutineDispatcher = TestCoroutineDispatcher()
    private val tested = OneSecondTickController(testCoroutineDispatcher)

    @Test
    fun `should emit an event every second for 3 seconds`() =
        testCoroutineDispatcher.runBlockingTest {
            // GIVEN
            val expectedTicks = 3
            var ticks = 0
            val tickCounterJob = launch {
                // THEN
                tested.tickFlow.collect {
                    ticks += 1
                }
            }

            // WHEN
            launch {
                tested.startTicker()
                advanceTimeBy(seconds(expectedTicks).inWholeMilliseconds)
                tested.stopTicker()
            }.join()

            // THEN
            tickCounterJob.cancelAndJoin()
            assertEquals(ticks, 3)
        }
}
