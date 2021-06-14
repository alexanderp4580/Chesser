package com.alexanderp.chesser.timer.data

import com.alexanderp.chesser.common.models.TimerConfig
import java.util.concurrent.TimeUnit
import kotlin.time.Duration
import kotlin.time.ExperimentalTime
import kotlin.time.toDuration

internal object TimerConfigsData {
    @OptIn(ExperimentalTime::class)
    val TIMER_CONFIGS = listOf(
        TimerConfig("Fischer Blitz", 5.toDuration(TimeUnit.MINUTES), Duration.ZERO),
        TimerConfig("Fischer", 5.toDuration(TimeUnit.MINUTES), 5.toDuration(TimeUnit.SECONDS))
    )
}