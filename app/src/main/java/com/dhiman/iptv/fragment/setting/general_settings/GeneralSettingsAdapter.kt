package com.dhiman.iptv.fragment.setting.general_settings

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.dhiman.iptv.R
import com.dhiman.iptv.data.model.SettingItem

class GeneralSettingsAdapter(
    private val items: MutableList<SettingItem>,
    private val onClick: (SettingItem) -> Unit
) : RecyclerView.Adapter<GeneralSettingsAdapter.ViewHolder>() {

    inner class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        private val icon: ImageView = view.findViewById(R.id.ivIcon)
        private val title: TextView = view.findViewById(R.id.tvTitle)

        fun bind(item: SettingItem) {
            icon.setImageResource(item.icon)
            title.text = item.title
            view.setOnClickListener { onClick(item) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context)
            .inflate(R.layout.row_general_setting_item, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount() = items.size

    fun updateList(newItems: List<SettingItem>) {
        items.clear()
        items.addAll(newItems)
        notifyDataSetChanged()
    }
}


