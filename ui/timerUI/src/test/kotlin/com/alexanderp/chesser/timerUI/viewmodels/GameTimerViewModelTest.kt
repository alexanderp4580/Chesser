package com.alexanderp.chesser.timerUI.viewmodels

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.SavedStateHandle
import com.alexanderp.chesser.common.models.ActivePlayer
import com.alexanderp.chesser.common.models.GameTimerState
import com.alexanderp.chesser.common.models.TimerConfig
import com.alexanderp.chesser.timer.usecases.EndGameUseCase
import com.alexanderp.chesser.timer.usecases.ObserveGameTimerStateUseCase
import com.alexanderp.chesser.timer.usecases.StartGameUseCase
import com.alexanderp.chesser.timer.usecases.TimerButtonClickedUseCase
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import java.util.concurrent.TimeUnit
import kotlin.test.assertEquals
import kotlin.time.ExperimentalTime
import kotlin.time.toDuration


@OptIn(ExperimentalCoroutinesApi::class)
@ExperimentalTime
internal class GameTimerViewModelTest {
    private val mockSavedStateHandle = mockk<SavedStateHandle> {
        every { get<TimerConfig>(any()) } returns TEST_TIMER_CONFIG
    }

    private val mockStartGameUseCase = mockk<StartGameUseCase>(relaxUnitFun = true)
    private val mockEndGameUseCase = mockk<EndGameUseCase>(relaxUnitFun = true)
    private val mockTimerButtonClickedUseCase = mockk<TimerButtonClickedUseCase>(relaxUnitFun = true)

    private val mockGameTimerStateFlow = MutableStateFlow<GameTimerState>(TEST_INITIAL_STATE)
    private val mockObserveGameTimerStateUseCase = mockk<ObserveGameTimerStateUseCase> {
        every { this@mockk.invoke() } returns mockGameTimerStateFlow
    }

    private val tested = GameTimerViewModel(
        mockSavedStateHandle,
        mockStartGameUseCase,
        mockEndGameUseCase,
        mockTimerButtonClickedUseCase,
        mockObserveGameTimerStateUseCase
    )

    @get:Rule
    val coroutineTestRule = CoroutineTestRule()

    @get:Rule
    val instantTaskExecutorRule: TestRule = InstantTaskExecutorRule()

    @Test
    fun `should start game when observing game state`() {
        coroutineTestRule.dispatcher.runBlockingTest {
            // WHEN
            val actualState = tested.gameTimerState.value

            // THEN
            assertEquals(TEST_INITIAL_STATE, actualState)
            verify {
                mockStartGameUseCase.invoke(TEST_TIMER_CONFIG)
                mockObserveGameTimerStateUseCase.invoke()
            }
        }
    }

    @Test
    fun `should send event on player turn`() {
        // WHEN
        val testPlayer = ActivePlayer.PlayerTwo
        tested.onTimerButtonPressed(testPlayer)

        // THEN
        verify { mockTimerButtonClickedUseCase.invoke(testPlayer) }
    }

    @Test
    fun `should start new game on restart`() {
        // WHEN
        tested.restartGame()

        // THEN
        verify { mockStartGameUseCase.invoke(TEST_TIMER_CONFIG) }
    }

    private companion object {
        val TEST_TIMER_CONFIG = TimerConfig("Test Config", 10.toDuration(TimeUnit.SECONDS), 5.toDuration(TimeUnit.SECONDS))
        val TEST_INITIAL_STATE = GameTimerState.Undefined
    }
}
