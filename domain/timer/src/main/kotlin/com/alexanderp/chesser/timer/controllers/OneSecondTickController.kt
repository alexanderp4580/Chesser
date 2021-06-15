package com.alexanderp.chesser.timer.controllers

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import mu.KotlinLogging
import javax.inject.Inject

internal class OneSecondTickController @Inject constructor(
    coroutineDispatcher: CoroutineDispatcher
) {
    private val _tickFlow = MutableSharedFlow<Unit>(replay = 0)
    val tickFlow: SharedFlow<Unit> = _tickFlow
    private val logger = KotlinLogging.logger {}
    private var tickerJob: Job? = null

    private val scope = CoroutineScope(coroutineDispatcher)

    fun startTicker() {
        logger.info { "startTicker" }
        tickerJob = scope.launch {
            while (true) {
                delay(ONE_SECOND_MS)
                _tickFlow.emit(Unit)
            }
        }
    }

    fun endTicker() {
        logger.info { "endTicker" }
        tickerJob?.cancel()
    }

    private companion object {
        const val ONE_SECOND_MS = 1000L
    }
}
