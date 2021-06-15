package com.alexanderp.chesser.timer.usecases

import com.alexanderp.chesser.common.models.GameTimerState
import com.alexanderp.chesser.common.models.TimerConfig
import com.alexanderp.chesser.timer.controllers.GameTimerController
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class ObserveGameTimerStateUseCase @Inject internal constructor(private val gameTimerController: GameTimerController) {
    operator fun invoke(): StateFlow<GameTimerState> {
        return gameTimerController.gameTimerStateFlow
    }
}
