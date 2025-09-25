package com.dhiman.iptv.activity.movie_program_list.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dhiman.iptv.activity.movie_program_list.MovieProgramListActivity
import com.dhiman.iptv.databinding.ItemCategoryBinding
import com.dhiman.iptv.util.gone
import com.dhiman.iptv.util.visible

class CategoryAdapter(
    private val categoryList: List<MovieProgramListActivity.Category>,var click:((String)-> Unit)?=null
) : RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>() {

    inner class CategoryViewHolder(val binding: ItemCategoryBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val binding = ItemCategoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CategoryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        val category = categoryList[position]
        holder.binding.txtCategoryName.text = category.categoryName
        if (category.isSeeAll){
            holder.binding.tvSeeAll.visible()
        }else holder.binding.tvSeeAll.gone()

        holder.binding.recyclerViewMovies.apply {
            val layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            this.layoutManager = layoutManager
            adapter = MovieAdapter(category.movies){
                click?.invoke(it)
            }

            holder.binding.btnLeft.setOnClickListener {
                val firstVisible = layoutManager.findFirstVisibleItemPosition()
                if (firstVisible > 0) {
                    smoothScrollToPosition(firstVisible - 1)
                }
            }

            holder.binding.btnRight.setOnClickListener {
                val lastVisible = layoutManager.findLastVisibleItemPosition()
                if (lastVisible < category.movies.size - 1) {
                    smoothScrollToPosition(lastVisible + 1)
                }
            }
        }


    }

    override fun getItemCount(): Int = categoryList.size
}
