package com.alexanderp.chesser.timer.controllers

import com.alexanderp.chesser.common.models.ActivePlayer
import com.alexanderp.chesser.common.models.GameTimerState
import com.alexanderp.chesser.common.models.TimerConfig
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import mu.KotlinLogging
import javax.inject.Inject
import kotlin.time.Duration
import kotlin.time.Duration.Companion.ZERO
import kotlin.time.Duration.Companion.seconds
import kotlin.time.ExperimentalTime

@OptIn(ExperimentalTime::class)
internal class GameTimerController @Inject constructor(
    private val oneSecondTickController: OneSecondTickController,
    coroutineDispatcher: CoroutineDispatcher,
) {
    private val coroutineScope = CoroutineScope(coroutineDispatcher)
    private val logger = KotlinLogging.logger {}

    private var currentGameState = DEFAULT_GAME_TIMER_STATE
    private val _gameTimerStateFlow: MutableStateFlow<GameTimerState> = MutableStateFlow(currentGameState)
    val gameTimerStateFlow: StateFlow<GameTimerState> = _gameTimerStateFlow
    lateinit var currentTimerConfig: TimerConfig
    private var tickerJob: Job? = null

    fun startGame(timerConfig: TimerConfig) {
        logger.info { "startGame" }
        currentTimerConfig = timerConfig

        // Set initial game state.
        currentGameState = GameTimerState(timerConfig.startTime, timerConfig.startTime, false, ActivePlayer.PlayerOne)
        updateGameTimerState()
    }

    fun endGame() {
        logger.info { "endGame" }
        oneSecondTickController.endTicker()
        tickerJob?.cancel()
    }

    fun timerButtonClicked(activePlayer: ActivePlayer) {
        logger.info { "timerButtonClicked. $activePlayer" }

        if (!currentGameState.isGamePlaying) {
            currentGameState = currentGameState.copy(isGamePlaying = true)
            launchTicker()
        } else {
            // Restart timer to give full second to every player.
            resetTicker()
        }

        addIncrement()

        currentGameState = currentGameState.copy(activePlayer = activePlayer)
        updateGameTimerState()
    }

    private fun launchTicker() {
        logger.info { "launchTicker" }
        tickerJob = coroutineScope.launch {
            oneSecondTickController.tickFlow.collect {
                onTick()
            }
        }
        oneSecondTickController.startTicker()
    }

    private fun resetTicker() {
        logger.info { "resetTicker" }
        oneSecondTickController.endTicker()
        oneSecondTickController.startTicker()
    }

    private fun onTick() {
        logger.info { "onTick" }
        decreaseSecondFromActivePlayer()
        updateGameTimerState()
    }

    private fun addIncrement() {
        val activePlayer = currentGameState.activePlayer

        logger.info { "Decreasing one second from $activePlayer" }
        currentGameState = when (activePlayer) {
            ActivePlayer.PlayerOne -> currentGameState.copy(playerOneTime = currentGameState.playerOneTime + currentTimerConfig.increment)
            ActivePlayer.PlayerTwo -> currentGameState.copy(playerTwoTime = currentGameState.playerTwoTime + currentTimerConfig.increment)
        }
    }

    private fun decreaseSecondFromActivePlayer() {
        val activePlayer = currentGameState.activePlayer


        logger.info { "Decreasing one second from $activePlayer" }
        currentGameState = when (activePlayer) {
            ActivePlayer.PlayerOne -> currentGameState.copy(playerOneTime = currentGameState.playerOneTime - seconds(1))
            ActivePlayer.PlayerTwo -> currentGameState.copy(playerTwoTime = currentGameState.playerTwoTime - seconds(1))
        }

        if (currentGameState.playerOneTime == ZERO || currentGameState.playerTwoTime == ZERO) {
            logger.info { "Game ended. Winner is ${activePlayer.otherPlayer()}" }
            currentGameState = currentGameState.copy(isGamePlaying = false)
            endGame()
        }
    }

    private fun updateGameTimerState() {
        _gameTimerStateFlow.value = currentGameState
    }

    private companion object {
        val DEFAULT_GAME_TIMER_STATE = GameTimerState(
            Duration.ZERO,
            Duration.ZERO,
            false,
            ActivePlayer.PlayerOne
        )
    }
}
