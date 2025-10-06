package com.dhiman.iptv.activity.live_program_list.adapter

import android.graphics.Typeface
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dhiman.iptv.databinding.ItemTimeEventBinding

class ScheduleAdapter(
    private val items: List<Pair<String, String>>
) : RecyclerView.Adapter<ScheduleAdapter.ScheduleViewHolder>() {

    private var focusedPosition: Int = RecyclerView.NO_POSITION

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ScheduleViewHolder {
        val binding = ItemTimeEventBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ScheduleViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ScheduleViewHolder, position: Int) {
        val (time, match) = items[position]
        holder.binding.tvTime.text = time
        holder.binding.tvMatch.text = match

        // Apply bold if focused
        if (position == focusedPosition) {
            holder.binding.tvTime.setTypeface(null, Typeface.BOLD)
            holder.binding.tvMatch.setTypeface(null, Typeface.BOLD)
        } else {
            holder.binding.tvTime.setTypeface(null, Typeface.NORMAL)
            holder.binding.tvMatch.setTypeface(null, Typeface.NORMAL)
        }

       /* holder.binding.root.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                val oldPosition = focusedPosition
                focusedPosition = position
                notifyItemChanged(oldPosition)
                notifyItemChanged(position)
            }
        }*/
    }

    override fun getItemCount(): Int = items.size

    inner class ScheduleViewHolder(val binding: ItemTimeEventBinding) :
        RecyclerView.ViewHolder(binding.root)
}
