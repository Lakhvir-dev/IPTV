package com.dhiman.iptv.activity.series_program.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dhiman.iptv.activity.series_program.SeriesProgramActivity
import com.dhiman.iptv.databinding.SeriesCategoryItemBinding


class SeriesCategoryAdapter(
    private val categories: List<SeriesProgramActivity.CategoryModel>,
    private val onClick: (SeriesProgramActivity.CategoryModel) -> Unit
) : RecyclerView.Adapter<SeriesCategoryAdapter.CategoryViewHolder>() {

    inner class CategoryViewHolder(val binding: SeriesCategoryItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val binding = SeriesCategoryItemBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return CategoryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        val category = categories[position]
        holder.binding.apply {
            tvCategoryName.text = category.categoryName
            tvCategoryCount.text = category.count.toString()
            root.setOnClickListener { onClick(category) }
        }
    }

    override fun getItemCount(): Int = categories.size
}
