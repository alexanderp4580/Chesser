package com.alexanderp.chesser.common.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlin.time.Duration
import kotlin.time.ExperimentalTime

/**
 * Timer configuration object that is used to start a new game session.
 *
 * This will contain all necessary information to create the timer and display the settings.
 */
@OptIn(ExperimentalTime::class)
@Parcelize
data class TimerConfig(val name: String, val startTime: Duration, val increment: Duration) : Parcelable
