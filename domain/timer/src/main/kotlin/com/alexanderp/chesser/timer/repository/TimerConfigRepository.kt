package com.alexanderp.chesser.timer.repository

import com.alexanderp.chesser.common.models.TimerConfig
import com.alexanderp.chesser.timer.data.TimerConfigsData
import mu.KotlinLogging

internal class TimerConfigRepository {
    private val logger = KotlinLogging.logger {}

    fun getTimerConfigs(): List<TimerConfig> {
        logger.info { "Getting timer configs" }
        return TimerConfigsData.TIMER_CONFIGS
    }
}
