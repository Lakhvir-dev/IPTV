package com.dhiman.iptv.activity.series_program

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.dhiman.iptv.activity.movie_program_list.MovieProgramListActivity.Category
import com.dhiman.iptv.activity.movie_program_list.MovieProgramListActivity.Movie
import com.dhiman.iptv.activity.movie_program_list.adapter.CategoryAdapter
import com.dhiman.iptv.activity.player.PlayerActivity
import com.dhiman.iptv.activity.series_program.adapter.SeriesCategoryAdapter
import com.dhiman.iptv.databinding.ActivitySeriesProgramBinding
import com.dhiman.iptv.util.ConstantUtil
import dagger.hilt.android.AndroidEntryPoint
import androidx.fragment.app.viewModels


@AndroidEntryPoint
class SeriesProgramActivity : Fragment()  {
    var binding: ActivitySeriesProgramBinding? = null
    lateinit var categoryAdapter: CategoryAdapter
    private val viewModel: SeriesProgramListViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = ActivitySeriesProgramBinding.inflate(inflater, container, false)
        binding?.lifecycleOwner = this
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.viewModel = viewModel
        putMoviesData()
        categoryAdapter()
        handleClicks()
    }

    fun handleClicks(){
        binding?.menuIcon?.setOnClickListener {
                startActivity(Intent(requireContext(), SeriesDetailsActivity::class.java))
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
            Toast.makeText(requireContext(), "Clicked: ${category.categoryName}", Toast.LENGTH_SHORT).show()
        }

        binding?.seriesCategoryRv?.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding?.seriesCategoryRv?.adapter = adapter

    }

    data class CategoryModel(
        val categoryName: String,
        val count: Int
    )
}