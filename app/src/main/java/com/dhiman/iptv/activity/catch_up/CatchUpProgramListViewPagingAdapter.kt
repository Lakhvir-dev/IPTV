package com.dhiman.iptv.activity.catch_up

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.PopupWindow
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.DataBindingUtil
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.dhiman.iptv.R
import com.dhiman.iptv.data.local.db.entity.LiveStreamCategory
import com.dhiman.iptv.databinding.ListItemEpgProgramMenuBinding
import com.dhiman.iptv.util.RecyclerViewClickListener
import com.dhiman.iptv.util.gone
import com.dhiman.iptv.util.visible
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.google.android.material.imageview.ShapeableImageView

class CatchUpProgramListViewPagingAdapter(private val clickListener: RecyclerViewClickListener) :
    PagingDataAdapter<LiveStreamCategory, CatchUpProgramListViewPagingAdapter.ViewHolder>(
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
                rlFavourite.gone()
                if (model.isFav) {
                    ivFav.gone()
                    ivFavFill.visible()
                } else {
                    ivFav.visible()
                    ivFavFill.gone()
                }

                try {
                    Glide.with(ivProgram.context)
                        .load(model.streamIcon)
                        .placeholder(R.drawable.horizontal_image_place_holder)
                        .error(R.drawable.horizontal_image_place_holder)
                        .override(ivProgram.width, ivProgram.height)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(ivProgram)
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
                        mainConstraintLayout.setBackgroundResource(R.drawable.select_bg_program_list)
                    } else {
                        mainConstraintLayout.setBackgroundResource(R.drawable.bg_program_list)
                    }
                }

                rlFavourite.setOnClickListener {
                    clickListener.onClick(it, position, model, position)
                }

                itemView.setOnLongClickListener {
                //    manageClick(it, absoluteAdapterPosition)
                    false
                }

            }
        }

    }

    private var popupWindow: PopupWindow? = null
    private fun manageClick(view: View, absoluteAdapterPosition: Int) {
        if (popupWindow == null) {
            popupWindow = PopupWindow()
        }

        popupWindow?.height = ViewGroup.LayoutParams.WRAP_CONTENT
        popupWindow?.width = ViewGroup.LayoutParams.WRAP_CONTENT
        popupWindow?.isFocusable = true

        val binding = DataBindingUtil.inflate<ListItemEpgProgramMenuBinding>(
            LayoutInflater.from(view.context),
            R.layout.list_item_epg_program_menu,
            null,
            false
        )

        popupWindow?.contentView = binding.root
        popupWindow?.showAsDropDown(view, 0, -view.height + popupWindow!!.height - 120)
        //  popupWindow?.showAsDropDown(view)
        //popupWindow?.showAtLocation(view, Gravity.TOP, 0, 0)

        val model = getItem(absoluteAdapterPosition)
        if (model!!.isFav) {
            binding.tvAddToFav.setText(R.string.remove_from_favourite)
        }

        binding.llPlay.setOnClickListener {
            popupWindow?.dismiss()
            it.context.apply {
                clickListener.onClick(it, absoluteAdapterPosition, model, -1)
            }
        }

        binding.llPlay.setOnFocusChangeListener { _, b ->
            if (b) {
                binding.llPlay.setBackgroundResource(R.color.storm_dust_alpha_65)
            } else {
                binding.llPlay.setBackgroundResource(R.color.transparent)
            }
        }

        binding.llAddToFavourite.setOnFocusChangeListener { _, b ->
            if (b) {
                binding.llAddToFavourite.setBackgroundResource(R.color.storm_dust_alpha_65)
            } else {
                binding.llAddToFavourite.setBackgroundResource(R.color.transparent)
            }
        }

        binding.llClose.setOnFocusChangeListener { _, b ->
            if (b) {
                binding.llClose.setBackgroundResource(R.color.storm_dust_alpha_65)
            } else {
                binding.llClose.setBackgroundResource(R.color.transparent)
            }
        }

        binding.llAddToFavourite.setOnClickListener {
            clickListener.onClick(it, absoluteAdapterPosition, model, absoluteAdapterPosition)
            popupWindow?.dismiss()
        }

        binding.llClose.setOnClickListener {
            popupWindow?.dismiss()
        }

    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CatchUpProgramListViewPagingAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item_program_list_view, parent, false)
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
        val mainConstraintLayout: ConstraintLayout = view.findViewById(R.id.mainConstraintLayout)
    }

    object ItemComparator : DiffUtil.ItemCallback<LiveStreamCategory>() {
        override fun areItemsTheSame(
            oldItem: LiveStreamCategory,
            newItem: LiveStreamCategory
        ): Boolean {
            return false
        }

        override fun areContentsTheSame(
            oldItem: LiveStreamCategory,
            newItem: LiveStreamCategory
        ): Boolean {
            return oldItem == newItem
        }
    }

}
