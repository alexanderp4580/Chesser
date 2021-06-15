package com.alexanderp.chesser.timerUI.ktx

import kotlin.time.Duration
import kotlin.time.ExperimentalTime


@OptIn(ExperimentalTime::class)
fun Duration.formatChessTime(): String {
    toComponents { hours, minutes, seconds, _ ->
        return if (hours > 0) {
            "${hours.padded()}:${minutes.padded()}:${seconds.padded()}"
        } else {
            "${minutes.padded()}:${seconds.padded()}"
        }
    }
}

private fun Int.padded() = toString().padStart(2, '0')
