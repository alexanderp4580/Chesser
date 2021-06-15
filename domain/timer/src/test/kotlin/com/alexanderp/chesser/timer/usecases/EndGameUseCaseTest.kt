package com.alexanderp.chesser.timer.usecases

import com.alexanderp.chesser.timer.controllers.GameTimerController
import io.mockk.mockk
import io.mockk.verify
import org.junit.Test

internal class EndGameUseCaseTest {
    private val mockGameTimerController = mockk<GameTimerController>(relaxUnitFun = true)
    private val tested = EndGameUseCase(mockGameTimerController)

    @Test
    fun `should call controller end game method`() {
        // WHEN
        tested.invoke()

        // THEN
        verify { mockGameTimerController.endGame() }
    }
}
