package com.dhiman.iptv.activity.epg_category

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dhiman.iptv.R

class ChannelAdapter(private val channels: List<Channel>) :
    RecyclerView.Adapter<ChannelAdapter.ChannelViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChannelViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_channel_row, parent, false)
        return ChannelViewHolder(view)
    }

    override fun getItemCount() = channels.size

    override fun onBindViewHolder(holder: ChannelViewHolder, position: Int) {
        holder.bind(channels[position])
    }

    inner class ChannelViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val logo = itemView.findViewById<ImageView>(R.id.channelLogo)
        private val programsRv = itemView.findViewById<RecyclerView>(R.id.programsRv)

        fun bind(channel: Channel) {
            logo.setImageResource(channel.logoRes)

            programsRv.layoutManager =
                LinearLayoutManager(itemView.context, RecyclerView.HORIZONTAL, false)
            programsRv.adapter = ProgramAdapter(channel.programs)
        }
    }
}
