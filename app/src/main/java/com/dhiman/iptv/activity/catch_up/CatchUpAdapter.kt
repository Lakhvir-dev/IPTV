package com.dhiman.iptv.activity.catch_up

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.dhiman.iptv.R
import com.dhiman.iptv.data.local.db.entity.LiveCategoryModel
import com.dhiman.iptv.databinding.ListItemCategoryBinding
import com.dhiman.iptv.util.OnFocusChangeListenerInterface
import com.dhiman.iptv.util.onFocusChange
import java.util.*
import kotlin.collections.ArrayList

class CatchUpAdapter(val dataList: List<LiveCategoryModel>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>(),
    Filterable {

    var filterDataList = ArrayList<LiveCategoryModel>()

    init {
        filterDataList.addAll(dataList)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return CatchUpViewHolder(
            ListItemCategoryBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        holder as CatchUpViewHolder

        val model = filterDataList[position]
        holder.apply {
            binding.apply {
                tvCategoryName.text = model.categoryName
            }

            itemView.setOnClickListener {
                it.context.apply {
//                    val intent = Intent(this, EpgProgramListActivity::class.java)
//                    intent.putExtra(ConstantUtil.INTENT_ID, model.categoryId)
//                    intent.putExtra(ConstantUtil.LIVE_PROGRAM_NAME, model.categoryName)
//                    startActivity(intent)
                }
            }

            itemView.onFocusChange(object : OnFocusChangeListenerInterface {
                override fun onFocus(view: View, value: Boolean) {
                    if (value) {
                        holder.itemView.setBackgroundResource(R.color.white_alpha)
                    } else {
                        holder.itemView.setBackgroundResource(R.color.transparent)
                    }
                }
            })
        }
    }

    override fun getItemCount(): Int {
        return filterDataList.size
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(p0: CharSequence?): FilterResults {
                val tempDataList = ArrayList<LiveCategoryModel>()
                filterDataList.clear()
                if (p0 == null || p0.isEmpty()) {
                    tempDataList.addAll(dataList)
                } else {
                    val filterPattern: String = p0.toString().lowercase(Locale.getDefault()).trim()
                    for (item in dataList) {
                        if (item.categoryName.lowercase(Locale.getDefault())
                                .contains(filterPattern)
                        ) {
                            tempDataList.add(item)
                        }
                    }
                }

                filterDataList.addAll(tempDataList)
                return FilterResults()
            }


            @SuppressLint("NotifyDataSetChanged")
            override fun publishResults(p0: CharSequence?, p1: FilterResults?) {
                notifyDataSetChanged()
            }
        }
    }

    inner class CatchUpViewHolder(val binding: ListItemCategoryBinding) :
        RecyclerView.ViewHolder(binding.root)
}