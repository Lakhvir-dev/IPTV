package com.dhiman.iptv.activity.live_program_list.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dhiman.iptv.databinding.ItemDateBinding

class DateAdapter(
    private val dates: List<String>,
    private val onDateClick: (String) -> Unit
) : RecyclerView.Adapter<DateAdapter.DateViewHolder>() {

    private var selectedPosition: Int = RecyclerView.NO_POSITION

    inner class DateViewHolder(val binding: ItemDateBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DateViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemDateBinding.inflate(inflater, parent, false)
        return DateViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DateViewHolder, position: Int) {
        val date = dates[position]
        holder.binding.tvDate.text = date

        // Apply selected state
        holder.binding.tvDate.isSelected = position == selectedPosition

        // Click listener
        holder.binding.root.setOnClickListener {
            val oldPos = selectedPosition
            selectedPosition = position

            notifyItemChanged(oldPos)
            notifyItemChanged(position)
            onDateClick(date)
        }
    }

    override fun getItemCount(): Int = dates.size
}

