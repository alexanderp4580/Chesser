package com.alexanderp.chesser.timer.repository

import com.alexanderp.chesser.common.models.TimerConfig
import com.alexanderp.chesser.timer.data.TimerConfigsData

internal class TimerRepository {
    fun getTimerConfigs(): List<TimerConfig> {
        return TimerConfigsData.TIMER_CONFIGS
    }
}