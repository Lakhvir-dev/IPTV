package com.dhiman.iptv.activity.catch_up

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.dhiman.iptv.R
import com.dhiman.iptv.data.local.db.entity.LiveCategoryModel

class CatchUpItemsNameListAdapter(
    private val programNameList: List<Any>,
    private val callback: ((Int) -> Unit)?
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item_program, parent, false)
        view.isFocusable = true
        view.isFocusableInTouchMode = true

        return ViewHolder(view)
//        return ViewHolder(
//            LayoutInflater.from(parent.context).inflate(R.layout.list_item_program, parent, false)
//        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        holder as ViewHolder
        val model = programNameList[position]
        if (model is LiveCategoryModel) {
            holder.tvProgramName.text = model.categoryName
        }

        holder.itemView.setOnClickListener {
            callback?.invoke(position)
        }
    }

    override fun getItemCount(): Int {
        return programNameList.size
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvProgramName: TextView = view.findViewById(R.id.channelCategoryName)
    }
}