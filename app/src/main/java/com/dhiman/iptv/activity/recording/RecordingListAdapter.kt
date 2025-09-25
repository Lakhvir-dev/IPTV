package com.dhiman.iptv.activity.recording

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dhiman.iptv.R
import com.dhiman.iptv.databinding.ListItemRecordingBinding
import com.dhiman.iptv.util.OnFocusChangeListenerInterface
import com.dhiman.iptv.util.onFocusChange

class RecordingListAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ViewHolder(
            ListItemRecordingBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        holder as ViewHolder
        holder.binding.tvFileName.text =
            "Recording File ${position.plus(1)}"
        holder.itemView.setOnClickListener {
            it.context.apply {
                //  startActivity(Intent(this, HomeActivity::class.java))
            }
        }
        holder.itemView.onFocusChange(object : OnFocusChangeListenerInterface {
            override fun onFocus(view: View, value: Boolean) {
                if (value) {
                    holder.itemView.setBackgroundResource(R.color.white_alpha)
                } else {
                    holder.itemView.setBackgroundResource(R.color.transparent)
                }
            }
        })
    }

    override fun getItemCount(): Int {
        return 10
    }

    inner class ViewHolder(val binding: ListItemRecordingBinding) :
        RecyclerView.ViewHolder(binding.root)
}