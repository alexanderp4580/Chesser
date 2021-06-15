package com.alexanderp.chesser.common.models

import kotlin.time.ExperimentalTime

/**
 * Holds the information about the current state of the game.
 */
@OptIn(ExperimentalTime::class)
sealed class GameTimerState {
    object Undefined : GameTimerState()
    data class Ready(val gameTime: GameTime) : GameTimerState()
    data class Playing(val gameTime: GameTime, val activePlayer: ActivePlayer) : GameTimerState()
    data class Ended(val gameTime: GameTime) : GameTimerState()
}
