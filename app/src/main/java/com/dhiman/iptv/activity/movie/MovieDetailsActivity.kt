package com.dhiman.iptv.activity.movie

import com.dhiman.iptv.activity.BaseActivity
import com.dhiman.iptv.activity.movie_program_list.MovieProgramListActivity.Movie
import com.dhiman.iptv.activity.series_program.adapter.PeopleAdapter
import com.dhiman.iptv.databinding.ActivityMovieDetailsBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MovieDetailsActivity :
    BaseActivity<ActivityMovieDetailsBinding>(ActivityMovieDetailsBinding::inflate) {
    override fun onActivityReady() {
        setPeopleAdapter()
    }

    fun setPeopleAdapter(){
        val recentlyAdded = listOf(
            Movie("Captain America", com.dhiman.iptv.R.drawable.actor1),
            Movie("Dora", com.dhiman.iptv.R.drawable.actor2),
            Movie("La La Land", com.dhiman.iptv.R.drawable.actress1),
            Movie("Captain Marvel", com.dhiman.iptv.R.drawable.actress2),
            Movie("Avengers", com.dhiman.iptv.R.drawable.atress2),
            Movie("Captain America", com.dhiman.iptv.R.drawable.actor1),
            Movie("Dora", com.dhiman.iptv.R.drawable.actor2),
            Movie("La La Land", com.dhiman.iptv.R.drawable.actress1),
            Movie("Captain Marvel", com.dhiman.iptv.R.drawable.actress2),
            Movie("Avengers", com.dhiman.iptv.R.drawable.atress2),
        )

        val adapter= PeopleAdapter(recentlyAdded)
        binding.castCrewRv.adapter=adapter
    }

}