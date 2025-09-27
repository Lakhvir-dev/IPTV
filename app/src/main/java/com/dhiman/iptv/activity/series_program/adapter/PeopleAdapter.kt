package com.dhiman.iptv.activity.series_program.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dhiman.iptv.activity.movie_program_list.MovieProgramListActivity
import com.dhiman.iptv.databinding.SeriesCastCrewItemBinding

class PeopleAdapter(
    private val movieList: List<MovieProgramListActivity.Movie>,var click:((String)-> Unit)?=null
) : RecyclerView.Adapter<PeopleAdapter.MovieViewHolder>() {

    inner class MovieViewHolder(val binding: SeriesCastCrewItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val binding = SeriesCastCrewItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MovieViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie = movieList[position]
        //holder.binding.txtTitle.text = movie.title
        movie.movieImage?.let { holder.binding.movieIv.setImageResource(it) }

        holder.binding.root.setOnClickListener {
        }
    }

    override fun getItemCount(): Int = movieList.size
}
