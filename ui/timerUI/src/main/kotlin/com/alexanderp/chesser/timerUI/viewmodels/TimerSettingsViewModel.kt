package com.alexanderp.chesser.timerUI.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.alexanderp.chesser.common.models.TimerConfig
import com.alexanderp.chesser.timer.usecases.GetTimerConfigsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TimerSettingsViewModel @Inject constructor(private val getTimerConfigsUseCase: GetTimerConfigsUseCase) : ViewModel() {
    private val _timerConfigs by lazy {
        val timerConfigs = MutableLiveData<List<TimerConfig>>()
        timerConfigs.value = getTimerConfigsUseCase()
        return@lazy timerConfigs
    }

    val timerConfigs: LiveData<List<TimerConfig>>
        get() = _timerConfigs
}
