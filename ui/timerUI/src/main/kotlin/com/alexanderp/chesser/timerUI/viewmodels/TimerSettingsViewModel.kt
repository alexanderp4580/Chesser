package com.alexanderp.chesser.homeUI.viewmodels

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import mu.KotlinLogging
import javax.inject.Inject

@HiltViewModel
class TimerSettingsViewModel @Inject constructor() : ViewModel() {
    private val logger = KotlinLogging.logger {}
    fun onClick() {
        logger.info { "CLICK" }
    }
}