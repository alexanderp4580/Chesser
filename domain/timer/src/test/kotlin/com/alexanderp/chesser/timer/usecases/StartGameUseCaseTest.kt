package com.alexanderp.chesser.timer.usecases

import com.alexanderp.chesser.common.models.TimerConfig
import com.alexanderp.chesser.timer.controllers.GameTimerController
import io.mockk.mockk
import io.mockk.verify
import org.junit.Test
import java.util.concurrent.TimeUnit
import kotlin.time.ExperimentalTime
import kotlin.time.toDuration

@OptIn(ExperimentalTime::class)
internal class StartGameUseCaseTest {
    private val mockGameTimerController = mockk<GameTimerController>(relaxUnitFun = true)
    private val tested = StartGameUseCase(mockGameTimerController)

    @Test
    fun `should call controller start game method`() {
        // WHEN
        val testTimerConfig = TimerConfig("Test Config", 10.toDuration(TimeUnit.SECONDS), 5.toDuration(TimeUnit.SECONDS))
        tested.invoke(testTimerConfig)

        // THEN
        verify { mockGameTimerController.startGame(testTimerConfig) }
    }
}
