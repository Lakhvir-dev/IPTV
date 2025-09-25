package com.dhiman.iptv.activity.m3u_playlist

import android.content.Intent
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
import com.dhiman.iptv.activity.player.PlayerActivity
import com.dhiman.iptv.data.local.db.entity.M3UItemModel
import com.dhiman.iptv.util.*
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.google.android.material.imageview.ShapeableImageView

class M3UPlaylistGridViewPagingAdapter(private val clickListener: RecyclerViewClickListener) :
    PagingDataAdapter<M3UItemModel, M3UPlaylistGridViewPagingAdapter.ViewHolder>(
        ItemComparator
    ) {
    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int
    ) {
        getItem(position)?.let { model ->
            holder.apply {
                tvProgramName.isSelected = true
                tvProgramName.text = model.itemName

                if (model.isFavorite) {
                    ivFav.gone()
                    ivFavFill.visible()
                } else {
                    ivFav.visible()
                    ivFavFill.gone()
                }

                try {
                    Glide.with(ivProgram.context)
                        .load(model.itemIcon)
                        .placeholder(R.drawable.vertical_image_place_holder_compress)
                        .error(R.drawable.vertical_image_place_holder_compress)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .fitCenter()
                        .into(ivProgram)
                } catch (e: Exception) {
                    e.printStackTrace()
                }

                itemView.setOnClickListener {
                    it.context.apply {
                        if (model.itemUrl.trim().isNotEmpty()) {
                            val intent = Intent(this, PlayerActivity::class.java)
                            intent.putExtra(ConstantUtil.INTENT_ID, model.itemUrl)
                            startActivity(intent)
                        } else {
                            "Invalid URL".shortToast(this)
                        }
                    }
                }

                itemView.setOnFocusChangeListener { _, isFocused ->
                    if(isFocused) {
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
    ): M3UPlaylistGridViewPagingAdapter.ViewHolder {
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
        val ivProgramRelativeLayout: RelativeLayout = view.findViewById(R.id.ivProgramRelativeLayout)
    }

    object ItemComparator : DiffUtil.ItemCallback<M3UItemModel>() {
        override fun areItemsTheSame(
            oldItem: M3UItemModel,
            newItem: M3UItemModel
        ): Boolean {
            return false
        }

        override fun areContentsTheSame(
            oldItem: M3UItemModel,
            newItem: M3UItemModel
        ): Boolean {
            return oldItem == newItem
        }
    }

}
