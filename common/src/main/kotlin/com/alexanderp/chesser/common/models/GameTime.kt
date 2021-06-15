package com.alexanderp.chesser.common.models

import kotlin.time.Duration
import kotlin.time.ExperimentalTime

@OptIn(ExperimentalTime::class)
data class GameTime constructor(
    val playerOneTime: Duration,
    val playerTwoTime: Duration,
)
