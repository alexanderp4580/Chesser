package com.alexanderp.chesser.common.models

import kotlin.time.ExperimentalTime

/**
 * Holds the information about the current state of the game.
 */
@OptIn(ExperimentalTime::class)
sealed class GameTimerState {
    /**
     * Undefined game state, might happen if game timer isn't started properly.
     */
    object Undefined : GameTimerState()

    /**
     * Game timer is ready to start, waiting for first play to make a move.
     */
    data class Ready(val gameTime: GameTime) : GameTimerState()

    /**
     * Game in progress.
     */
    data class Playing(val gameTime: GameTime, val activePlayer: ActivePlayer) : GameTimerState()

    /**
     * Game has ended.
     */
    data class Ended(val gameTime: GameTime) : GameTimerState()
}
