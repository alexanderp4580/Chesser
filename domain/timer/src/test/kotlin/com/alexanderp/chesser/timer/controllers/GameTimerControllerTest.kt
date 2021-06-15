package com.alexanderp.chesser.timer.controllers

import com.alexanderp.chesser.common.models.ActivePlayer
import com.alexanderp.chesser.common.models.GameTimerState
import com.alexanderp.chesser.common.models.TimerConfig
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Test
import java.util.concurrent.TimeUnit
import kotlin.test.assertEquals
import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds
import kotlin.time.ExperimentalTime
import kotlin.time.toDuration


@ExperimentalTime
@OptIn(ExperimentalCoroutinesApi::class)
internal class GameTimerControllerTest {
    private val testTickFlow = MutableSharedFlow<Unit>()
    private val mockOneSecondTickController = mockk<OneSecondTickController>(relaxUnitFun = true) {
        every { tickFlow } returns testTickFlow
    }
    private val testCoroutineDispatcher = TestCoroutineDispatcher()

    private val tested = GameTimerController(mockOneSecondTickController, testCoroutineDispatcher)

    @Test
    fun `should start sending game state after start`() {
        testCoroutineDispatcher.runBlockingTest {
            // WHEN
            tested.startGame(TEST_TIMER_CONFIG)
            val actualGameState = tested.gameTimerStateFlow.first() as GameTimerState.Ready

            // THEN
            assertEquals(TEST_TIMER_CONFIG.startTime, actualGameState.gameTime.playerOneTime)
            assertEquals(TEST_TIMER_CONFIG.startTime, actualGameState.gameTime.playerTwoTime)
        }
    }

    @Test
    fun `should start game after first move and decrease time`() {
        testCoroutineDispatcher.runBlockingTest {
            // GIVEN
            tested.startGame(TEST_TIMER_CONFIG)

            // WHEN
            val testPlayer = ActivePlayer.PlayerOne
            tested.timerButtonClicked(testPlayer)
            var actualGameState = tested.gameTimerStateFlow.first() as GameTimerState.Playing

            // THEN
            verify { mockOneSecondTickController.startTicker() }
            assertEquals(TEST_TIMER_CONFIG.startTime, actualGameState.gameTime.playerOneTime)
            assertEquals(TEST_TIMER_CONFIG.startTime, actualGameState.gameTime.playerTwoTime)

            // WHEN
            testTickFlow.emit(Unit) // One second passed
            actualGameState = tested.gameTimerStateFlow.first() as GameTimerState.Playing

            // THEN
            assertEquals(TEST_TIMER_CONFIG.startTime - seconds(1), actualGameState.gameTime.playerOneTime)
            assertEquals(TEST_TIMER_CONFIG.startTime, actualGameState.gameTime.playerTwoTime)
        }
    }

    @Test
    fun `should add increment after second turn`() {
        testCoroutineDispatcher.runBlockingTest {
            // GIVEN
            tested.startGame(TEST_TIMER_CONFIG)
            tested.timerButtonClicked(ActivePlayer.PlayerOne)

            // WHEN
            tested.timerButtonClicked(ActivePlayer.PlayerTwo)
            val actualGameState = tested.gameTimerStateFlow.first() as GameTimerState.Playing

            // THEN
            assertEquals(TEST_TIMER_CONFIG.startTime, actualGameState.gameTime.playerTwoTime)
            assertEquals(TEST_TIMER_CONFIG.startTime + TEST_TIMER_CONFIG.increment, actualGameState.gameTime.playerOneTime)
        }
    }

    @Test
    fun `should end game if time runs out`() {
        testCoroutineDispatcher.runBlockingTest {
            // GIVEN
            tested.startGame(TEST_TIMER_CONFIG)
            tested.timerButtonClicked(ActivePlayer.PlayerOne)

            // WHEN
            repeat(TEST_TIMER_CONFIG.startTime.inWholeSeconds.toInt()) {
                testTickFlow.emit(Unit)
            }
            val actualGameState = tested.gameTimerStateFlow.first() as GameTimerState.Ended

            // THEN
            assertEquals(Duration.ZERO, actualGameState.gameTime.playerOneTime)
            assertEquals(TEST_TIMER_CONFIG.startTime, actualGameState.gameTime.playerTwoTime)
        }
    }

    @Test
    fun `should stop ticker on end game`() {
        // GIVEN
        tested.startGame(TEST_TIMER_CONFIG)
        tested.timerButtonClicked(ActivePlayer.PlayerOne)

        // WHEN
        tested.endGame()

        // THEN
        verify { mockOneSecondTickController.endTicker() }
    }

    private companion object {
        val TEST_TIMER_CONFIG = TimerConfig("Test Config", 10.toDuration(TimeUnit.SECONDS), 5.toDuration(TimeUnit.SECONDS))
    }
}
