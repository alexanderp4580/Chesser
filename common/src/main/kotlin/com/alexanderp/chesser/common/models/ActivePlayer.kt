package com.alexanderp.chesser.common.models

/**
 * Enum for determining whose turn it is.
 */
enum class ActivePlayer {
    PlayerOne,
    PlayerTwo;

    /**
     * Return the other player from the selected one.
     */
    fun otherPlayer(): ActivePlayer =
        when (this) {
            PlayerOne -> PlayerTwo
            PlayerTwo -> PlayerOne
        }
}
