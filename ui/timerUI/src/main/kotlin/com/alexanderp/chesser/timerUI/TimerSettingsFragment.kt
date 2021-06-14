package com.alexanderp.chesser.timerUI

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.alexanderp.chesser.common.models.TimerConfig
import com.alexanderp.chesser.common.ui.viewBinding
import com.alexanderp.chesser.timerUI.adapter.TimerConfigAdapter
import com.alexanderp.chesser.timerUI.databinding.FragmentTimerSettingsBinding
import com.alexanderp.chesser.timerUI.viewmodels.TimerSettingsViewModel
import dagger.hilt.android.AndroidEntryPoint
import mu.KotlinLogging

@AndroidEntryPoint
class TimerSettingsFragment : Fragment(R.layout.fragment_timer_settings) {
    private val binding: FragmentTimerSettingsBinding by viewBinding()
    private val viewModel: TimerSettingsViewModel by viewModels()
    private val timerConfigAdapter = TimerConfigAdapter()
    private val logger = KotlinLogging.logger {}

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        logger.info { "onViewCreated" }
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            with(timerPresetSelectionList) {
                adapter = timerConfigAdapter
            }
        }

        timerConfigAdapter.setItems(viewModel.getTimerConfigurations())
    }
}