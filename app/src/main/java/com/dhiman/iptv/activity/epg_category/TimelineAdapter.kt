package com.dhiman.iptv.activity.epg_category

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.dhiman.iptv.R

class TimelineAdapter(private val times: List<String>) :
    RecyclerView.Adapter<TimelineAdapter.TimeViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TimeViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_timeline, parent, false)
        return TimeViewHolder(view)
    }

    override fun getItemCount() = times.size

    override fun onBindViewHolder(holder: TimeViewHolder, position: Int) {
        holder.bind(times[position])
    }

    inner class TimeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val timeText = itemView.findViewById<TextView>(R.id.timeText)

        fun bind(time: String) {
            timeText.text = time
        }
    }
}
