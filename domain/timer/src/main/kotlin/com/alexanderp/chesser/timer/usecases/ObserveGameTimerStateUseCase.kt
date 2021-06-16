package com.alexanderp.chesser.timer.usecases

import com.alexanderp.chesser.common.models.GameTimerState
import com.alexanderp.chesser.timer.controllers.GameTimerController
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

/**
 * Observe current game state. This flow will contain all relevant information about the game for presentation purposes.
 */
class ObserveGameTimerStateUseCase @Inject internal constructor(private val gameTimerController: GameTimerController) {
    operator fun invoke(): StateFlow<GameTimerState> {
        return gameTimerController.gameTimerStateFlow
    }
}
