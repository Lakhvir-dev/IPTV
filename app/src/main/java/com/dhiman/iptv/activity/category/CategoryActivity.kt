package com.dhiman.iptv.activity.category

import android.content.Intent
import android.view.View
import androidx.activity.viewModels
import com.dhiman.iptv.activity.BaseActivity
import com.dhiman.iptv.activity.catch_up.CatchUpAdapter
import com.dhiman.iptv.activity.catch_up.CatchUpChannelsActivity
import com.dhiman.iptv.activity.movie_program_list.MovieProgramListActivity
import com.dhiman.iptv.activity.movie_program_list.MovieProgramListActivity.Movie
import com.dhiman.iptv.activity.movie_program_list.MovieProgramListViewModel
import com.dhiman.iptv.activity.movie_program_list.adapter.MovieAdapter
import com.dhiman.iptv.activity.series_program.SeriesProgramActivity
import com.dhiman.iptv.data.local.db.entity.LiveCategoryModel
import com.dhiman.iptv.databinding.CategotyActivityBinding
import com.dhiman.iptv.util.RecyclerViewClickListener
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CategoryActivity : BaseActivity<CategotyActivityBinding>(
    CategotyActivityBinding::inflate
), RecyclerViewClickListener {

    var type=""
    private val viewModel: MovieProgramListViewModel by viewModels()

    override fun onActivityReady() {
        binding.viewModel = viewModel
        setCategoryAdapter()
        setCategoryNameAdapter()
        getIntentData()
    }

    override fun onClick(
        view: View,
        position: Int,
        selectedModel: Any,
        childPosition: Int
    ) {
    }

    fun getIntentData(){
        if (intent.hasExtra("type")){
           type =  intent.getStringExtra("type")?:""
        }
    }

    fun setCategoryNameAdapter() {
        val categories = listOf(
            LiveCategoryModel(categoryName = "All", categoryId = "1400"),
            LiveCategoryModel(categoryName = "Favourites", categoryId = "0"),
            LiveCategoryModel(categoryName = "Recently Added", categoryId = "20"),
            LiveCategoryModel(categoryName = "New Released", categoryId = "36"),
            LiveCategoryModel(categoryName = "Action", categoryId = "34"),
            LiveCategoryModel(categoryName = "Adventure", categoryId = "24"),
            LiveCategoryModel(categoryName = "Sci-fi", categoryId = "45"),
            LiveCategoryModel(categoryName = "Mystery", categoryId = "13"),
            LiveCategoryModel(categoryName = "Horror", categoryId = "34")
        )

        val adapter = CatchUpAdapter(categories)
        binding.rvCategories.adapter = adapter
    }

    fun setCategoryAdapter() {

        val favourites = listOf(
            Movie("Avengers", com.dhiman.iptv.R.drawable.avengers),
            Movie("Captain Marvel", com.dhiman.iptv.R.drawable.captain_marvel),
            Movie("Captain America", com.dhiman.iptv.R.drawable.captain_america),
            Movie("La La Land", com.dhiman.iptv.R.drawable.wolworine),
            Movie("Dora", com.dhiman.iptv.R.drawable.thor),
            Movie("Avengers", com.dhiman.iptv.R.drawable.avengers),
            Movie("Captain Marvel", com.dhiman.iptv.R.drawable.captain_marvel),
            Movie("Captain America", com.dhiman.iptv.R.drawable.captain_america),
            Movie("La La Land", com.dhiman.iptv.R.drawable.wolworine),
            Movie("Dora", com.dhiman.iptv.R.drawable.thor),
            Movie("Avengers", com.dhiman.iptv.R.drawable.avengers),
        )
        val adapter = MovieAdapter(favourites){
                val intent = Intent(this, CatchUpChannelsActivity::class.java).putExtra("type",type)
                startActivity(intent)
        }
            binding.rvMovies.adapter = adapter
        }

    }