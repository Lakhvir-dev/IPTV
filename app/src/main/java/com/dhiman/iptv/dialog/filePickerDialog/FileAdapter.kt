package com.dhiman.iptv.dialog.filePickerDialog

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.dhiman.iptv.R

class FileAdapter internal constructor(
    private val mContext: Context,
    private val id: Int,
    private val items: List<Option>
) : ArrayAdapter<Option>(mContext, id, items) {

    override fun getItem(position: Int): Option {
        return items[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, view: View?, parent: ViewGroup): View {
        var convertView = view
        val holder: ViewHolder
        if (convertView == null) {
            val layoutInflater = LayoutInflater.from(mContext)
            convertView = layoutInflater.inflate(id, null)
            holder = ViewHolder()
            holder.textViewName = convertView!!.findViewById(R.id.TextView01)

            convertView.tag = holder
        } else {
            holder = convertView.tag as ViewHolder
        }

        val option = items[position]
        holder.textViewName!!.text = option.name
        return convertView
    }

    internal class ViewHolder {
        var textViewName: TextView? = null
    }

}

