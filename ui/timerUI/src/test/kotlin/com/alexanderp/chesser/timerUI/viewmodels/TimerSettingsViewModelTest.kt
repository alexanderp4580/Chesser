package com.alexanderp.chesser.timerUI.viewmodels

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.alexanderp.chesser.common.models.TimerConfig
import com.alexanderp.chesser.timer.usecases.GetTimerConfigsUseCase
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import java.util.concurrent.TimeUnit
import kotlin.test.assertEquals
import kotlin.time.ExperimentalTime
import kotlin.time.toDuration

@ExperimentalTime
@ExperimentalCoroutinesApi
internal class TimerSettingsViewModelTest {
    private val mockGetTimerConfigsUseCase = mockk<GetTimerConfigsUseCase> {
        every { this@mockk.invoke() } returns TEST_TIMER_CONFIGS
    }
    private val tested = TimerSettingsViewModel(mockGetTimerConfigsUseCase)

    @get:Rule
    val coroutineTestRule = CoroutineTestRule()

    @get:Rule
    val instantTaskExecutorRule: TestRule = InstantTaskExecutorRule()

    @Test
    fun `should load timer configs on observation`() {
        // WHEN
        val actualTimerConfigs = tested.timerConfigs.value

        // THEN
        assertEquals(TEST_TIMER_CONFIGS, actualTimerConfigs)
    }

    private companion object {
        val TEST_TIMER_CONFIGS = listOf(TimerConfig("Test Config", 10.toDuration(TimeUnit.SECONDS), 5.toDuration(TimeUnit.SECONDS)))

    }
}
