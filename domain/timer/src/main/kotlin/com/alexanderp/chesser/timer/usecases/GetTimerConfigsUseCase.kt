package com.alexanderp.chesser.timer.usecases

import com.alexanderp.chesser.common.models.TimerConfig
import com.alexanderp.chesser.timer.repository.TimerConfigRepository
import javax.inject.Inject

class GetTimerConfigsUseCase @Inject internal constructor(private val timerRepository: TimerConfigRepository) {
    operator fun invoke(): List<TimerConfig> {
        return timerRepository.getTimerConfigs()
    }
}
