package com.dhiman.iptv.activity.live_program_list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.dhiman.iptv.R
import com.dhiman.iptv.data.local.db.entity.LiveCategoryModel
import com.dhiman.iptv.util.OnFocusChangeListenerInterface
import com.dhiman.iptv.util.onFocusChange

class LiveProgramNameListNewAdapter(
    private val programNameList: List<LiveCategoryModel>,
    private val callback: ((Int) -> Unit)?
) : RecyclerView.Adapter<LiveProgramNameListNewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.channels_category_item, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.channelCategoryName.text = programNameList[position].categoryName
        holder.itemView.setOnClickListener {
            callback?.invoke(position)
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
        return programNameList.size
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val channelCategoryName: TextView = view.findViewById(R.id.categoryNameTv)
    }
}