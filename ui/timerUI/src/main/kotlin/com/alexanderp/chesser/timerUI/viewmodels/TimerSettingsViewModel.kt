package com.alexanderp.chesser.timerUI.viewmodels

import androidx.lifecycle.ViewModel
import com.alexanderp.chesser.common.models.TimerConfig
import com.alexanderp.chesser.timer.usecases.GetTimerConfigsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TimerSettingsViewModel @Inject constructor(private val getTimerConfigsUseCase: GetTimerConfigsUseCase) : ViewModel() {
    fun getTimerConfigurations(): List<TimerConfig> {
        return getTimerConfigsUseCase()
    }
}