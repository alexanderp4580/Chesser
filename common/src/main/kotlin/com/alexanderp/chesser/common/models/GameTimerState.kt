package com.alexanderp.chesser.common.models

import kotlin.time.Duration
import kotlin.time.ExperimentalTime

/**
 * Holds the information about the current state of the game.
 */
@OptIn(ExperimentalTime::class)
data class GameTimerState(
    val playerOneTime: Duration,
    val playerTwoTime: Duration,
    val isGamePlaying: Boolean,
    val activePlayer: ActivePlayer,
)
