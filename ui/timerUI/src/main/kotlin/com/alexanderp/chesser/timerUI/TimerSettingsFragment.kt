package com.alexanderp.chesser.timerUI

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.alexanderp.chesser.common.models.TimerConfig
import com.alexanderp.chesser.common.ui.viewBinding
import com.alexanderp.chesser.timerUI.adapter.TimerConfigAdapter
import com.alexanderp.chesser.timerUI.databinding.FragmentTimerSettingsBinding
import com.alexanderp.chesser.timerUI.viewmodels.TimerSettingsViewModel
import dagger.hilt.android.AndroidEntryPoint
import mu.KotlinLogging

/**
 * Timer settings selection fragment, here the user can choose which timer configuration and settings are needed
 * for the chess game.
 */
@AndroidEntryPoint
class TimerSettingsFragment : Fragment(R.layout.fragment_timer_settings) {
    private val binding: FragmentTimerSettingsBinding by viewBinding()
    private val viewModel: TimerSettingsViewModel by viewModels()
    private val timerConfigAdapter = TimerConfigAdapter(::onTimerConfigSelected)

    private val logger = KotlinLogging.logger {}

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        logger.info { "onViewCreated" }
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            timerPresetSelectionList.adapter = timerConfigAdapter
        }

        viewModel.timerConfigs.observe(viewLifecycleOwner, this::renderTimerConfigs)
    }

    private fun onTimerConfigSelected(timerConfig: TimerConfig) {
        logger.info { "TimerConfig clicked. $timerConfig" }
        findNavController().navigate(TimerSettingsFragmentDirections.actionTimerSettingsFragmentToGameTimerFragment(timerConfig))
    }

    private fun renderTimerConfigs(configs: List<TimerConfig>) {
        // Populate configurations list.
        timerConfigAdapter.setItems(configs)
    }
}
