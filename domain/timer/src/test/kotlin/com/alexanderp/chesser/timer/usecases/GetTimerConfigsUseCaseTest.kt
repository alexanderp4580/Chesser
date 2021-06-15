package com.alexanderp.chesser.timer.usecases

import com.alexanderp.chesser.common.models.TimerConfig
import com.alexanderp.chesser.timer.repository.TimerConfigRepository
import io.mockk.every
import io.mockk.mockk
import org.junit.Test
import java.util.concurrent.TimeUnit
import kotlin.test.assertEquals
import kotlin.time.ExperimentalTime
import kotlin.time.toDuration

@OptIn(ExperimentalTime::class)
internal class GetTimerConfigsUseCaseTest {
    private val mockTimerConfigRepository = mockk<TimerConfigRepository> {
        every { getTimerConfigs() } returns TEST_TIMER_CONFIGS
    }
    private val tested = GetTimerConfigsUseCase(mockTimerConfigRepository)

    @Test
    fun `should get all available timer configs from repository`() {
        // WHEN
        val actualConfigs = tested.invoke()

        // THEN
        assertEquals(TEST_TIMER_CONFIGS, actualConfigs)
    }

    private companion object {
        val TEST_TIMER_CONFIGS =
            listOf(TimerConfig("Test Config", 10.toDuration(TimeUnit.SECONDS), 5.toDuration(TimeUnit.SECONDS)))
    }
}
