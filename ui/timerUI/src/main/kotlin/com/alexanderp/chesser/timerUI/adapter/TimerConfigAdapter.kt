package com.alexanderp.chesser.timerUI.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.alexanderp.chesser.common.models.TimerConfig
import com.alexanderp.chesser.timerUI.databinding.TimerConfigItemBinding

internal class TimerConfigAdapter : RecyclerView.Adapter<TimerConfigViewHolder>() {
    private val timerConfigs: MutableList<TimerConfig> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TimerConfigViewHolder {
        val itemBinding = TimerConfigItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TimerConfigViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: TimerConfigViewHolder, position: Int) {
        holder.bind(timerConfigs[position])
    }

    override fun getItemCount(): Int = timerConfigs.size

    fun setItems(items: List<TimerConfig>) {
        timerConfigs.clear()
        timerConfigs.addAll(items)
        notifyDataSetChanged()
    }
}