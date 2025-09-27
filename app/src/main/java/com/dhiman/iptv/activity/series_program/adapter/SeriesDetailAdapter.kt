package com.dhiman.iptv.activity.series_program.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dhiman.iptv.activity.movie_program_list.MovieProgramListActivity
import com.dhiman.iptv.databinding.SeriesEpisodeItemBinding


class SeriesDetailAdapter(
    private val movieList: List<MovieProgramListActivity.Movie>,var click:((String)-> Unit)?=null
) : RecyclerView.Adapter<SeriesDetailAdapter.MovieViewHolder>() {

    inner class MovieViewHolder(val binding: SeriesEpisodeItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val binding = SeriesEpisodeItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MovieViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie = movieList[position]
        //holder.binding.txtTitle.text = movie.title
        movie.movieImage?.let { holder.binding.episodeThumbnailIv.setImageResource(it) }

        holder.binding.root.setOnClickListener {
            click?.invoke("https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/WeAreGoingOnBullrun.mp4")
        }
    }

    override fun getItemCount(): Int = movieList.size
}
