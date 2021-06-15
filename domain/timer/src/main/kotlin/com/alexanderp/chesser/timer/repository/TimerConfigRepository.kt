package com.alexanderp.chesser.timer.repository

import com.alexanderp.chesser.common.models.TimerConfig
import com.alexanderp.chesser.timer.data.TimerConfigsData

internal class TimerConfigRepository {
    fun getTimerConfigs(): List<TimerConfig> {
        return TimerConfigsData.TIMER_CONFIGS
    }
}
