package com.alexanderp.chesser.timerUI

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.alexanderp.chesser.homeUI.viewmodels.TimerSettingsViewModel
import com.alexanderp.chesser.timerUI.TimerSettingsFragmentArgs
import com.alexanderp.chesser.timerUI.databinding.FragmentTimerSettingsBinding
import mu.KotlinLogging

class TimerSettingsFragment : Fragment() {
    private lateinit var binding: FragmentTimerSettingsBinding
    private val timerSettingsViewModel: TimerSettingsViewModel by viewModels()

    private val args by navArgs<TimerSettingsFragmentArgs>()

    private val logger = KotlinLogging.logger {}
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentTimerSettingsBinding.inflate(inflater)

        return binding.root
    }
}