package com.dhiman.iptv.activity.series_program

import android.content.Intent
import androidx.activity.viewModels
import com.dhiman.iptv.activity.BaseActivity
import com.dhiman.iptv.activity.movie_program_list.MovieProgramListActivity.Movie
import com.dhiman.iptv.activity.movie_program_list.adapter.MovieAdapter
import com.dhiman.iptv.activity.player.PlayerActivity
import com.dhiman.iptv.activity.series_program.adapter.PeopleAdapter
import com.dhiman.iptv.activity.series_program.adapter.SeriesDetailAdapter
import com.dhiman.iptv.databinding.ActivitySeriesDetailsBinding
import com.dhiman.iptv.util.ConstantUtil
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class SeriesDetailsActivity : BaseActivity<ActivitySeriesDetailsBinding>(
    ActivitySeriesDetailsBinding::inflate
) {
    private val viewModel: SeriesDetailsViewModel by viewModels()

    override fun onActivityReady() {
        binding.viewModel = viewModel
        setMoviesAdapter()
        setPeopleAdapter()
    }

    fun setMoviesAdapter(){
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

        val adapter= SeriesDetailAdapter(recentlyAdded) {
            val intent = Intent(this, PlayerActivity::class.java)
            intent.putExtra(ConstantUtil.INTENT_ID, it)
            startActivity(intent)
        }
        binding.episodesRv.adapter=adapter
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