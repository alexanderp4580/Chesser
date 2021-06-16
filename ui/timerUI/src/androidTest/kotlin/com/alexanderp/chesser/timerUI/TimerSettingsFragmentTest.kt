package com.alexanderp.chesser.timerUI

import androidx.fragment.app.testing.FragmentScenario
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.lifecycle.Lifecycle
import androidx.test.ext.junit.runners.AndroidJUnit4
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@HiltAndroidTest
internal class TimerSettingsFragmentTest {
    private lateinit var fragmentScenario: FragmentScenario<TimerSettingsFragment>

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Before
    fun setup() {
        fragmentScenario = launchFragmentInContainer()
    }

    @Test
    fun shouldObserveTimerConfigsOnCreateView() {
        // TODO: Finish writing test
        fragmentScenario.moveToState(Lifecycle.State.STARTED)
    }
}
