package com.dhiman.iptv.activity.live_program_list.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.dhiman.iptv.R
import com.dhiman.iptv.data.model.ChannelUnderCategoryModel

class ChannelUnderCategoryAdapter(
    private val cannelNameList: List<ChannelUnderCategoryModel>,
    private val callback: ((Int) -> Unit)?
) : RecyclerView.Adapter<ChannelUnderCategoryAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.channel_item, parent, false)

        return ViewHolder(view)
    }

    @SuppressLint("SetTextI18n", "ClickableViewAccessibility")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.index.text = (position+1).toString()
        holder.channelName.text = cannelNameList[position].channelName
        holder.channelDescription.text = cannelNameList[position].channelDesc
        holder.itemView.setOnClickListener {
            callback?.invoke(position)
        }

        holder.seekBar.setOnTouchListener { _, _ -> true }

    }

    override fun getItemCount(): Int {
        return cannelNameList.size
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val channelName: TextView = view.findViewById(R.id.channelNameTv)
        val index: TextView = view.findViewById(R.id.channelNumberTv)
        val seekBar: SeekBar = view.findViewById(R.id.seekbar)
        val channelDescription: TextView = view.findViewById(R.id.channelDescriptionTv)
    }
}