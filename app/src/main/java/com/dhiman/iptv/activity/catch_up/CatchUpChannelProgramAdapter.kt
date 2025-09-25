package com.dhiman.iptv.activity.catch_up

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.dhiman.iptv.R
import com.dhiman.iptv.data.model.catchUp.EpgListings
import com.dhiman.iptv.databinding.ListItemCatchUpChannelBinding
import com.dhiman.iptv.util.OnFocusChangeListenerInterface
import com.dhiman.iptv.util.decodeBase64
import com.dhiman.iptv.util.getConvertedDateTime
import com.dhiman.iptv.util.onFocusChange

class CatchUpChannelProgramAdapter(
    val dataList: List<EpgListings>,
    val callback: (Int, EpgListings) -> Unit
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = DataViewHolder(
            ListItemCatchUpChannelBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

        binding.itemView.isFocusable = true
        binding.itemView.isFocusableInTouchMode = true

        return binding
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        holder as DataViewHolder

        val model = dataList[position]
        holder.apply {
            binding.tvProgramNameTextView.text = model.title?.decodeBase64()
            binding.tvDetailTextView.text = model.description?.decodeBase64()
            binding.tvTimeTextView.text =
                model.start?.getConvertedDateTime(
                    "yyyy-MM-dd hh:mm:ss",
                    "MMM dd, hh:mm a"
                ) + " - " +
                        model.end?.getConvertedDateTime(
                            "yyyy-MM-dd hh:mm:ss", "MMM dd, hh:mm a"
                        )

            itemView.onFocusChange(object : OnFocusChangeListenerInterface {
                override fun onFocus(view: View, value: Boolean) {
                    if (value) {
                        itemView.setBackgroundResource(R.drawable.black_stroke_drawable)
                    } else {
                        itemView.setBackgroundColor(
                            ContextCompat.getColor(
                                itemView.context,
                                R.color.black_cow
                            )
                        )
                    }
                }
            })

            itemView.setOnClickListener {
                callback.invoke(position, model)
            }

        }
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    inner class DataViewHolder(val binding: ListItemCatchUpChannelBinding) :
        RecyclerView.ViewHolder(binding.root)

}