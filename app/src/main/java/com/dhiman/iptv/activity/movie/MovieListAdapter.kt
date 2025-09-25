package com.dhiman.iptv.activity.movie

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dhiman.iptv.R
import com.dhiman.iptv.activity.movie_program_list.MovieProgramListActivity
import com.dhiman.iptv.data.local.db.entity.VideoStreamCategory
import com.dhiman.iptv.databinding.ListItemProgramGridViewBinding
import com.dhiman.iptv.util.gone
import com.dhiman.iptv.util.visible
import com.squareup.picasso.Picasso

class MovieListAdapter(
    val dataList: MutableList<VideoStreamCategory>,
    val movieProgramListActivity: MovieProgramListActivity
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return LiveListViewHolder(
            ListItemProgramGridViewBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        holder as LiveListViewHolder

        val model = dataList[position]
        holder.binding.apply {
            channelCategoryName.isSelected = true
            channelCategoryName.text = model.name

            if (model.isFav) {
                ivFav.gone()
                ivFavFill.visible()
            } else {
                ivFav.visible()
                ivFavFill.gone()
            }

            try {
                if (model.streamIcon?.isNotEmpty() == true) {
                    Picasso.get()
                        .load(model.streamIcon)
                        .placeholder(R.drawable.vertical_image_place_holder)
                        .into(ivProgram)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    inner class LiveListViewHolder(val binding: ListItemProgramGridViewBinding) :
        RecyclerView.ViewHolder(binding.root)
}