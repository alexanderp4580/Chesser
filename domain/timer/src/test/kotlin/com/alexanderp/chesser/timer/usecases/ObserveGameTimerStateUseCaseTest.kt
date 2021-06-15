package com.alexanderp.chesser.timer.usecases

import com.alexanderp.chesser.common.models.GameTime
import com.alexanderp.chesser.common.models.GameTimerState
import com.alexanderp.chesser.timer.controllers.GameTimerController
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.MutableStateFlow
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.time.Duration
import kotlin.time.ExperimentalTime

@OptIn(ExperimentalTime::class)
internal class ObserveGameTimerStateUseCaseTest {
    private val testGameTimerStateFlow = MutableStateFlow<GameTimerState>(GameTimerState.Undefined)
    private val mockGameTimerController = mockk<GameTimerController> {
        every { gameTimerStateFlow } returns testGameTimerStateFlow
    }

    private val tested = ObserveGameTimerStateUseCase(mockGameTimerController)

    @Test
    fun `should pass game timer state`() {
        // GIVEN
        val actualFlow = tested()

        // WHEN
        testGameTimerStateFlow.value = TEST_GAME_TIMER_STATE

        // THEN
        assertEquals(TEST_GAME_TIMER_STATE, actualFlow.value)
    }

    private companion object {
        val TEST_GAME_TIMER_STATE = GameTimerState.Ready(GameTime(Duration.ZERO, Duration.ZERO))
    }
}
