package com.alexanderp.chesser.common.models

import kotlin.time.Duration
import kotlin.time.ExperimentalTime

/**
 * Timer configuration object that is used to start a new game session.
 *
 * This will contain all necessary information to create the timer and display the settings.
 */
@OptIn(ExperimentalTime::class)
data class TimerConfig constructor(val name: String, val startTime: Duration, val delay: Duration)