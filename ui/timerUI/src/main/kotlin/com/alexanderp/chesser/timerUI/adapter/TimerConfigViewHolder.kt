package com.alexanderp.chesser.timerUI.adapter

import androidx.recyclerview.widget.RecyclerView
import com.alexanderp.chesser.common.models.TimerConfig
import com.alexanderp.chesser.timerUI.databinding.TimerConfigItemBinding
import kotlin.time.ExperimentalTime

class TimerConfigViewHolder(private val binding: TimerConfigItemBinding) : RecyclerView.ViewHolder(binding.root) {
    @OptIn(ExperimentalTime::class)
    fun bind(timerConfig: TimerConfig) {
        binding.configName.text = timerConfig.name
        binding.configTime.text = timerConfig.startTime.toString()
        binding.configDelay.text = timerConfig.delay.toString()
    }
}