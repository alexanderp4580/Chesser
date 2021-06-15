package com.alexanderp.chesser.timer.usecases

import com.alexanderp.chesser.common.models.ActivePlayer
import com.alexanderp.chesser.timer.controllers.GameTimerController
import javax.inject.Inject

class TimerButtonClickedUseCase @Inject internal constructor(private val gameTimerController: GameTimerController) {
    operator fun invoke(activePlayer: ActivePlayer) {
        return gameTimerController.timerButtonClicked(activePlayer)
    }
}
