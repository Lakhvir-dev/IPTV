package com.dhiman.iptv.activity.series_program

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.dhiman.iptv.R
import com.dhiman.iptv.data.local.db.entity.SeriesStreamCategory
import com.dhiman.iptv.util.RecyclerViewClickListener
import com.dhiman.iptv.util.gone
import com.dhiman.iptv.util.visible
import com.bumptech.glide.Glide
import com.google.android.material.imageview.ShapeableImageView

class SeriesProgramGridViewPagingAdapter(private val clickListener: RecyclerViewClickListener) :
    PagingDataAdapter<SeriesStreamCategory, SeriesProgramGridViewPagingAdapter.ViewHolder>(
        ItemComparator
    ) {
    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int
    ) {
        getItem(position)?.let { model ->
            holder.apply {
                tvProgramName.isSelected = true
                tvProgramName.text = model.name

                if (model.isFav) {
                    ivFav.gone()
                    ivFavFill.visible()
                } else {
                    ivFav.visible()
                    ivFavFill.gone()
                }

                try {
                    if (model.cover.isNotEmpty()) {
                        ivProgram.setImageDrawable(null)
                        Glide.with(ivProgram.context)
                            .load(model.cover)
                            .placeholder(R.mipmap.tv_vertical_image_place_holder)
                            .into(ivProgram)

//                        Picasso.get()
//                            .load(model.cover)
//                            .placeholder(R.drawable.vertical_image_place_holder)
//                            .into(ivProgram)
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }

                itemView.setOnClickListener {
                    it.context.apply {
                        clickListener.onClick(it, absoluteAdapterPosition, model, -1)
                    }
                }

                itemView.setOnFocusChangeListener { _, isFocused ->
                    if (isFocused) {
                        ivProgramRelativeLayout.setBackgroundResource(R.drawable.select_bg_program_list)
                    } else {
                        ivProgramRelativeLayout.setBackgroundResource(R.drawable.bg_program_list)
                    }
                }

                rlFavourite.setOnClickListener {
                    clickListener.onClick(it, position, model, position)
                }

            }
        }

    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): SeriesProgramGridViewPagingAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item_program_grid_view, parent, false)
        view.isFocusable = true
        view.isFocusableInTouchMode = true

        return ViewHolder(view)
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvProgramName: TextView = view.findViewById(R.id.channelCategoryName)
        val rlFavourite: RelativeLayout = view.findViewById(R.id.rlFavourite)
        val ivFav: ImageView = view.findViewById(R.id.ivFav)
        val ivFavFill: ImageView = view.findViewById(R.id.ivFavFill)
        val ivProgram: ShapeableImageView = view.findViewById(R.id.ivProgram)
        val ivProgramRelativeLayout: RelativeLayout =
            view.findViewById(R.id.ivProgramRelativeLayout)
    }

    object ItemComparator : DiffUtil.ItemCallback<SeriesStreamCategory>() {
        override fun areItemsTheSame(
            oldItem: SeriesStreamCategory,
            newItem: SeriesStreamCategory
        ): Boolean {
            return false
        }

        override fun areContentsTheSame(
            oldItem: SeriesStreamCategory,
            newItem: SeriesStreamCategory
        ): Boolean {
            return oldItem == newItem
        }
    }

}