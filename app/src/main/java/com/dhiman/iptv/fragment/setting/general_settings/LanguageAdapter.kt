package com.dhiman.iptv.fragment.setting.general_settings

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.dhiman.iptv.R
import com.dhiman.iptv.data.model.SettingItem

class LanguageAdapter(
    private val items: MutableList<SettingItem>,
    private val onClick: (SettingItem) -> Unit
) : RecyclerView.Adapter<LanguageAdapter.ViewHolder>() {

    var selected =-1

    inner class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        private val icon: ImageView = view.findViewById(R.id.ivIcon)
        private val title: TextView = view.findViewById(R.id.tvTitle)
        private val tvStatus: TextView = view.findViewById(R.id.tvStatus)
        private val ivArrow: ImageView = view.findViewById(R.id.ivArrow)

        fun bind(item: SettingItem, position1: Int) {
            ivArrow.visibility =View.INVISIBLE
            tvStatus.visibility =View.GONE
            icon.setImageResource(item.icon)
            title.text = item.title
            if (selected == position1){
                icon.visibility =View.VISIBLE
            }else {
                icon.visibility =View.INVISIBLE
            }


            view.setOnClickListener {
                selected = position1
                onClick(item)
                notifyDataSetChanged()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context)
            .inflate(R.layout.row_general_setting_item, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position], position)
    }

    override fun getItemCount() = items.size

    fun updateList(newItems: List<SettingItem>) {
        items.clear()
        items.addAll(newItems)
        notifyDataSetChanged()
    }
}


