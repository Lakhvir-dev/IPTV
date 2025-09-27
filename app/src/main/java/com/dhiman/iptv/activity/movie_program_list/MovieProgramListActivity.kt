package com.dhiman.iptv.activity.movie_program_list

import android.content.Intent
import android.view.View
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.dhiman.iptv.activity.BaseActivity
import com.dhiman.iptv.activity.movie.MovieDetailsActivity
import com.dhiman.iptv.activity.movie_program_list.adapter.CategoryAdapter
import com.dhiman.iptv.activity.player.PlayerActivity
import com.dhiman.iptv.databinding.ActivityMovieProgramListBinding
import com.dhiman.iptv.util.ConstantUtil
import com.dhiman.iptv.util.RecyclerViewClickListener
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MovieProgramListActivity : BaseActivity<ActivityMovieProgramListBinding>(
    ActivityMovieProgramListBinding::inflate
), RecyclerViewClickListener {

    lateinit var categoryAdapter: CategoryAdapter
    private val viewModel: MovieProgramListViewModel by viewModels()

    override fun onActivityReady() {
        binding.viewModel = viewModel
        putMoviesData()
        setClicks()
    }

    override fun onClick(
        view: View,
        position: Int,
        selectedModel: Any,
        childPosition: Int
    ) {

    }

    fun setClicks(){
        binding.menuIcon.setOnClickListener {
            startActivity(Intent(this@MovieProgramListActivity, MovieDetailsActivity::class.java))
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

    data class Movie(
        var movieName: String="",
        var movieImage: Int?=null,
    )

    data class Category(
        val categoryName: String,
        val movies: List<Movie>,
        val isSeeAll: Boolean=false
    )

}