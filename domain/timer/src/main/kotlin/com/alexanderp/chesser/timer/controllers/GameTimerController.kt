package com.alexanderp.chesser.timer.controllers

import com.alexanderp.chesser.common.models.ActivePlayer
import com.alexanderp.chesser.common.models.GameTime
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

    private lateinit var currentGameState: GameTimerState
    private val _gameTimerStateFlow: MutableStateFlow<GameTimerState> = MutableStateFlow(GameTimerState.Undefined)
    val gameTimerStateFlow: StateFlow<GameTimerState> = _gameTimerStateFlow
    lateinit var currentTimerConfig: TimerConfig
    private var tickerJob: Job? = null

    fun startGame(timerConfig: TimerConfig) {
        logger.info { "startGame" }
        currentTimerConfig = timerConfig

        // Set initial game state.
        currentGameState = GameTimerState.Ready(GameTime(timerConfig.startTime, timerConfig.startTime))
        updateGameTimerState()
    }

    fun endGame() {
        logger.info { "endGame" }
        oneSecondTickController.endTicker()
        tickerJob?.cancel()
    }

    fun timerButtonClicked(newActivePlayer: ActivePlayer) {
        logger.info { "timerButtonClicked. $newActivePlayer" }

        when (val state = currentGameState) {
            is GameTimerState.Ready -> {
                currentGameState = GameTimerState.Playing(state.gameTime, newActivePlayer)
                launchTicker()
            }
            is GameTimerState.Playing -> {
                if (state.activePlayer == newActivePlayer) {
                    logger.error { "Same player clicked twice." }
                    return
                }
                addIncrement(state.copy(activePlayer = newActivePlayer))

                // Restart timer to give full second to every player.
                resetTicker()
            }
            else -> Unit
        }
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
        val state = currentGameState
        if (state is GameTimerState.Playing) {
            decreaseSecond(state)
        }
        updateGameTimerState()
    }

    private fun addIncrement(state: GameTimerState.Playing) {
        currentTimerConfig.increment.takeIf { it.isPositive() }?.let { increment ->
            logger.info { "Adding Increment $increment" }
            val playerOneIncrease = if (state.activePlayer == ActivePlayer.PlayerOne) increment else ZERO
            val playerTwoIncrease = if (state.activePlayer == ActivePlayer.PlayerTwo) increment else ZERO
            val newGameTime = GameTime(
                playerOneTime = state.gameTime.playerOneTime + playerOneIncrease,
                playerTwoTime = state.gameTime.playerTwoTime + playerTwoIncrease
            )

            currentGameState = state.copy(gameTime = newGameTime)
        }
    }

    private fun decreaseSecond(state: GameTimerState.Playing) {
        logger.info { "Decreasing one second from ${state.activePlayer}" }
        val playerOneDecrease = if (state.activePlayer == ActivePlayer.PlayerOne) seconds(1) else ZERO
        val playerTwoDecrease = if (state.activePlayer == ActivePlayer.PlayerTwo) seconds(1) else ZERO
        val newGameTime = GameTime(
            playerOneTime = state.gameTime.playerOneTime - playerOneDecrease,
            playerTwoTime = state.gameTime.playerTwoTime - playerTwoDecrease
        )

        currentGameState = if (newGameTime.playerOneTime == ZERO || newGameTime.playerTwoTime == ZERO) {
            logger.info { "Game ended. Winner is ${state.activePlayer.otherPlayer()}" }
            endGame()
            GameTimerState.Ended(newGameTime)
        } else {
            state.copy(gameTime = newGameTime)
        }
    }

    private fun updateGameTimerState() {
        logger.info("Sending game state: $currentGameState")
        _gameTimerStateFlow.value = currentGameState
    }
}
