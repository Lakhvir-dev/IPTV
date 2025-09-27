package com.dhiman.iptv.activity.series_program

import android.content.Intent
import android.widget.Toast
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.dhiman.iptv.activity.BaseActivity
import com.dhiman.iptv.activity.movie_program_list.MovieProgramListActivity.Category
import com.dhiman.iptv.activity.movie_program_list.MovieProgramListActivity.Movie
import com.dhiman.iptv.activity.movie_program_list.adapter.CategoryAdapter
import com.dhiman.iptv.activity.player.PlayerActivity
import com.dhiman.iptv.activity.series_program.adapter.SeriesCategoryAdapter
import com.dhiman.iptv.databinding.ActivitySeriesProgramBinding
import com.dhiman.iptv.util.ConstantUtil
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SeriesProgramActivity : BaseActivity<ActivitySeriesProgramBinding>(
    ActivitySeriesProgramBinding::inflate
) {
    lateinit var categoryAdapter: CategoryAdapter
    private val viewModel: SeriesProgramListViewModel by viewModels()

    override fun onActivityReady() {
        binding.viewModel = viewModel
        putMoviesData()
        categoryAdapter()
        handleClicks()
    }

    fun handleClicks(){
        binding.menuIcon.setOnClickListener {
            startActivity(Intent(this@SeriesProgramActivity, SeriesDetailsActivity::class.java))
        }
    }

    fun putMoviesData(){
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

        val recentlyAdded = listOf(
            Movie("Captain America", com.dhiman.iptv.R.drawable.captain_america),
            Movie("Dora", com.dhiman.iptv.R.drawable.thor),
            Movie("La La Land", com.dhiman.iptv.R.drawable.wolworine),
            Movie("Captain Marvel", com.dhiman.iptv.R.drawable.captain_marvel),
            Movie("Avengers", com.dhiman.iptv.R.drawable.avengers),
            Movie("Captain America", com.dhiman.iptv.R.drawable.captain_america),
            Movie("Dora", com.dhiman.iptv.R.drawable.thor),
            Movie("La La Land", com.dhiman.iptv.R.drawable.wolworine),
            Movie("La La Land", com.dhiman.iptv.R.drawable.wolworine),
            Movie("Captain Marvel", com.dhiman.iptv.R.drawable.captain_marvel),
            Movie("Avengers", com.dhiman.iptv.R.drawable.avengers),
        )

        val categoryList = listOf(
            Category("Favourites", favourites, isSeeAll = true),
            Category("Recently Added", recentlyAdded)
        )

        binding.movieGridRv.layoutManager = LinearLayoutManager(this)
        categoryAdapter=CategoryAdapter(categoryList){url->
            val intent = Intent(this, PlayerActivity::class.java)
            intent.putExtra(ConstantUtil.INTENT_ID, url)
            startActivity(intent)
        }
        binding.movieGridRv.adapter = categoryAdapter

    }

    fun categoryAdapter(){
        val categoryList = listOf(
            CategoryModel("All", 144551),
            CategoryModel("Favourites", 1),
            CategoryModel("Recently Watched", 13),
            CategoryModel("New Released", 18),
            CategoryModel("Action", 453),
            CategoryModel("Adventure", 334),
            CategoryModel("Sci-fi", 455),
            CategoryModel("Mystery", 776),
            CategoryModel("Horror", 367)
        )

        val adapter = SeriesCategoryAdapter(categoryList) { category ->
            Toast.makeText(this, "Clicked: ${category.categoryName}", Toast.LENGTH_SHORT).show()
        }

        binding.seriesCategoryRv.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        binding.seriesCategoryRv.adapter = adapter

    }

    data class CategoryModel(
        val categoryName: String,
        val count: Int
    )
}