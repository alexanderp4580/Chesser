package com.alexanderp.chesser.timerUI.adapter

import android.annotation.SuppressLint
import androidx.recyclerview.widget.RecyclerView
import com.alexanderp.chesser.common.models.TimerConfig
import com.alexanderp.chesser.timerUI.databinding.TimerConfigItemBinding
import com.alexanderp.chesser.timerUI.ktx.formatChessTime
import mu.KotlinLogging
import kotlin.time.DurationUnit
import kotlin.time.ExperimentalTime

typealias TimerConfigClickListener = (TimerConfig) -> Unit

internal class TimerConfigViewHolder(
    private val binding: TimerConfigItemBinding,
    private val timerConfigClickListener: TimerConfigClickListener
) : RecyclerView.ViewHolder(binding.root) {

    private val logger = KotlinLogging.logger {}

    @SuppressLint("SetTextI18n")
    @OptIn(ExperimentalTime::class)
    fun bind(timerConfig: TimerConfig) {
        logger.info { "Binding: $timerConfig" }
        with(binding) {
            configName.text = timerConfig.name
            configTime.text = timerConfig.startTime.formatChessTime()

            configIncrement.text = timerConfig.increment.toString(DurationUnit.SECONDS)
            configContainer.setOnClickListener {
                timerConfigClickListener.invoke(timerConfig)
            }
        }
    }
}
