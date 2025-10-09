package com.dhiman.iptv.activity.movie_program_list

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.dhiman.iptv.activity.movie.MovieDetailsActivity
import com.dhiman.iptv.activity.movie_program_list.adapter.CategoryAdapter
import com.dhiman.iptv.activity.player.PlayerActivity
import com.dhiman.iptv.databinding.ActivityMovieProgramListBinding
import com.dhiman.iptv.util.ConstantUtil
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MovieProgramListActivity : Fragment() {

    lateinit var categoryAdapter: CategoryAdapter
    private val viewModel: MovieProgramListViewModel by viewModels()
    var binding: ActivityMovieProgramListBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= ActivityMovieProgramListBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.viewModel=viewModel
        setClicks()
        putMoviesData()
    }
    fun setClicks(){
        binding?.menuIcon?.setOnClickListener {
            startActivity(Intent(requireContext(), MovieDetailsActivity::class.java))
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

        binding?.movieGridRv?.layoutManager = LinearLayoutManager(requireContext())
        categoryAdapter=CategoryAdapter(categoryList){url->
            val intent = Intent(requireContext(), PlayerActivity::class.java)
            intent.putExtra(ConstantUtil.INTENT_ID, url)
            startActivity(intent)
        }
        binding?.movieGridRv?.adapter = categoryAdapter

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