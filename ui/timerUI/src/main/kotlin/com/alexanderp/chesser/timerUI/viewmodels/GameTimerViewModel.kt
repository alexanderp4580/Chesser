package com.alexanderp.chesser.timerUI.viewmodels

import androidx.lifecycle.LiveData
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
    private val startGameUseCase: StartGameUseCase,
    private val endGameUseCase: EndGameUseCase,
    private val timerButtonClickedUseCase: TimerButtonClickedUseCase,
    observeGameTimerStateUseCase: ObserveGameTimerStateUseCase
) : ViewModel() {
    val gameTimerState: LiveData<GameTimerState> = observeGameTimerStateUseCase().asLiveData(viewModelScope)
    private val logger = KotlinLogging.logger {}

    fun startGame(timerConfig: TimerConfig) {
        startGameUseCase(timerConfig)
    }

    fun onTimerButtonPressed(activePlayer: ActivePlayer) {
        timerButtonClickedUseCase(activePlayer)
    }

    override fun onCleared() {
        super.onCleared()
        endGameUseCase()
    }
}
