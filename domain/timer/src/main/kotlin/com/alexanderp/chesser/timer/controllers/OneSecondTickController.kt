package com.alexanderp.chesser.timer.controllers

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import mu.KotlinLogging
import javax.inject.Inject
import kotlin.time.Duration.Companion.seconds
import kotlin.time.ExperimentalTime

/**
 * Tick controller for emitting an event every second.
 *
 * This can be used to drive the chess timer.
 */
@OptIn(ExperimentalTime::class)
internal class OneSecondTickController @Inject constructor(
    coroutineDispatcher: CoroutineDispatcher
) {
    private val _tickFlow = MutableSharedFlow<Unit>(replay = 0)
    val tickFlow: SharedFlow<Unit> = _tickFlow

    private val logger = KotlinLogging.logger {}
    private val scope = CoroutineScope(coroutineDispatcher)
    private var tickerJob: Job? = null

    fun startTicker() {
        logger.info { "startTicker" }
        tickerJob = scope.launch {
            while (isActive) {
                delay(seconds(1).inWholeMilliseconds)
                logger.info { "Tick" }
                _tickFlow.emit(Unit)
            }
        }
    }

    fun stopTicker() {
        logger.info { "endTicker" }
        tickerJob?.cancel()
    }
}
