package com.dhiman.iptv.activity.my_play_list

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dhiman.iptv.R
import com.dhiman.iptv.data.local.db.entity.UserDataModel
import com.dhiman.iptv.databinding.ListItemCategoryBinding
import com.dhiman.iptv.util.OnFocusChangeListenerInterface
import com.dhiman.iptv.util.RecyclerViewClickListener
import com.dhiman.iptv.util.onFocusChange

class MyPlayListAdapter(
    val dataList: List<UserDataModel>,
    val clickListener: RecyclerViewClickListener
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var selectedIndex = 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = ListItemCategoryBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        binding.root.isFocusableInTouchMode = true
        binding.root.isFocusable = true
        return ViewHolder(
            binding
        )
    }

    @SuppressLint("SetTextI18n", "NotifyDataSetChanged")
    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        @SuppressLint("RecyclerView") position: Int
    ) {
        holder as ViewHolder
        val model = dataList[position]
        holder.apply {
            binding.apply {
                tvCategoryName.text = model.userModel.playListName + "-" + model.userModel.userName

                ivLive.setOnClickListener {
                    selectedIndex = position
                    clickListener.onClick(itemView, position, model, -1)
                    notifyDataSetChanged()
                }
            }

            itemView.setOnClickListener {
                it.context.apply {
                    selectedIndex = position
                    clickListener.onClick(itemView, position, model, position)
                    notifyDataSetChanged()
                }
            }

            if (position == selectedIndex) {
                itemView.requestFocus()
                itemView.setBackgroundResource(R.color.white_alpha)
            } else {
                itemView.setBackgroundResource(R.color.transparent)
            }

            itemView.onFocusChange(object : OnFocusChangeListenerInterface {
                override fun onFocus(view: View, value: Boolean) {
                    if (value) {
                        itemView.setBackgroundResource(R.drawable.black_stroke_drawable)
                    } else {
                        if (position == selectedIndex) {
                            itemView.setBackgroundResource(R.color.white_alpha)
                        } else {
                            itemView.setBackgroundResource(R.color.transparent)
                        }
                    }
                }
            })
        }
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    inner class ViewHolder(val binding: ListItemCategoryBinding) :
        RecyclerView.ViewHolder(binding.root)
}