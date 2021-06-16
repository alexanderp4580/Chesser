package com.alexanderp.chesser.timer.usecases

import com.alexanderp.chesser.timer.controllers.GameTimerController
import javax.inject.Inject

/**
 * End game event that will clean up the controller.
 *
 * @note This must be sent when game data is no longer needed and should be destroyed.
 */
class EndGameUseCase @Inject internal constructor(private val gameTimerController: GameTimerController) {
    operator fun invoke() {
        return gameTimerController.endGame()
    }
}
