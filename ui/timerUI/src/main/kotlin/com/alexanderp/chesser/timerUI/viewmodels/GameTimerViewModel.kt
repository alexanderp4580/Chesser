package com.alexanderp.chesser.timerUI.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alexanderp.chesser.common.extensions.asLiveData
import com.alexanderp.chesser.common.models.ActivePlayer
import com.alexanderp.chesser.common.models.GameTimerState
import com.alexanderp.chesser.common.models.TimerConfig
import com.alexanderp.chesser.timer.usecases.EndGameUseCase
import com.alexanderp.chesser.timer.usecases.ObserveGameTimerStateUseCase
import com.alexanderp.chesser.timer.usecases.StartGameUseCase
import com.alexanderp.chesser.timer.usecases.TimerButtonClickedUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import mu.KotlinLogging
import javax.inject.Inject

@HiltViewModel
class GameTimerViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val startGameUseCase: StartGameUseCase,
    private val endGameUseCase: EndGameUseCase,
    private val timerButtonClickedUseCase: TimerButtonClickedUseCase,
    observeGameTimerStateUseCase: ObserveGameTimerStateUseCase
) : ViewModel() {
    private val savedStateTimerConfig = savedStateHandle.get<TimerConfig>(SAVED_STATE_TIMER_CONFIG_KEY)!!
    val gameTimerState: LiveData<GameTimerState> by lazy {
        startGameUseCase(savedStateTimerConfig)
        observeGameTimerStateUseCase().asLiveData(viewModelScope)
    }

    private val logger = KotlinLogging.logger {}

    fun onTimerButtonPressed(activePlayer: ActivePlayer) {
        logger.info { "onTimerButtonPressed" }
        timerButtonClickedUseCase(activePlayer)
    }

    fun restartGame() {
        logger.info { "restartGame" }
        startGameUseCase(savedStateTimerConfig)
    }

    override fun onCleared() {
        logger.info { "onCleared" }
        super.onCleared()
        endGameUseCase()
    }

    private companion object {
        const val SAVED_STATE_TIMER_CONFIG_KEY = "timerConfig"
    }
}
