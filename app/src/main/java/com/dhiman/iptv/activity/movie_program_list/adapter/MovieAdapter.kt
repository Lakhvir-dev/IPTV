package com.dhiman.iptv.activity.movie_program_list.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dhiman.iptv.activity.movie_program_list.MovieProgramListActivity
import com.dhiman.iptv.databinding.MovieItemBinding

class MovieAdapter(
    private val movieList: List<MovieProgramListActivity.Movie>
) : RecyclerView.Adapter<MovieAdapter.MovieViewHolder>() {

    inner class MovieViewHolder(val binding: MovieItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val binding = MovieItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MovieViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie = movieList[position]
        //holder.binding.txtTitle.text = movie.title
        movie.movieImage?.let { holder.binding.movieIv.setImageResource(it) }
    }

    override fun getItemCount(): Int = movieList.size
}
