package com.alexanderp.chesser.timer.usecases

import com.alexanderp.chesser.common.models.ActivePlayer
import com.alexanderp.chesser.timer.controllers.GameTimerController
import io.mockk.mockk
import io.mockk.verify
import org.junit.Test

internal class TimerButtonClickedUseCaseTest {
    private val mockGameTimerController = mockk<GameTimerController>(relaxUnitFun = true)
    private val tested = TimerButtonClickedUseCase(mockGameTimerController)

    @Test
    fun `should call controller timer button clicked method`() {
        // WHEN
        val testPlayer = ActivePlayer.PlayerTwo
        tested.invoke(testPlayer)

        // THEN
        verify { mockGameTimerController.timerButtonClicked(testPlayer) }
    }
}
