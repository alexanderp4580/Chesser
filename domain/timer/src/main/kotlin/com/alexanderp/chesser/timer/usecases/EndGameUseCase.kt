package com.alexanderp.chesser.timer.usecases

import com.alexanderp.chesser.common.models.TimerConfig
import com.alexanderp.chesser.timer.controllers.GameTimerController
import javax.inject.Inject

class EndGameUseCase @Inject internal constructor(private val gameTimerController: GameTimerController) {
    operator fun invoke() {
        return gameTimerController.endGame()
    }
}
