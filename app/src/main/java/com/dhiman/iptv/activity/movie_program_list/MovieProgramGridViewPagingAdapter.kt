package com.dhiman.iptv.activity.movie_program_list

import android.app.Activity
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.PopupWindow
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.dhiman.iptv.R
import com.dhiman.iptv.data.local.db.entity.VideoStreamCategory
import com.dhiman.iptv.databinding.ListItemEpgProgramMenuBinding
import com.dhiman.iptv.util.RecyclerViewClickListener
import com.dhiman.iptv.util.gone
import com.dhiman.iptv.util.visible
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.google.android.material.imageview.ShapeableImageView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class MovieProgramGridViewPagingAdapter(
    private val clickListener: RecyclerViewClickListener
) :
    PagingDataAdapter<VideoStreamCategory, MovieProgramGridViewPagingAdapter.ViewHolder>(
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
                    if (model.streamIcon?.isNotEmpty() == true) {
                        //ivProgram.load(model.streamIcon)

                        ivProgram.setImageDrawable(null)

                        //     (ivProgram.context as Activity).runOnUiThread {
                        Glide.with(ivProgram.context)
                            .load(model.streamIcon)
                            .placeholder(R.mipmap.tv_vertical_image_place_holder)
                            .into(ivProgram)
                        //   }

//                        picasso.load(model.streamIcon)
//                            .resize(125,200)
//                            .centerCrop()
//                            .placeholder(R.drawable.vertical_image_place_holder)
//                            .into(ivProgram)

//                        imageLoad(ivProgram, model.streamIcon) { bitmap, imageView ->
//                            imageView.setImageBitmap(bitmap)
//                        }

//                        Picasso.get()
//                            .load(model.streamIcon)
//                            .resize(125,200)
//                            .centerCrop()
//                            .placeholder(R.drawable.vertical_image_place_holder)
//                            .into(ivProgram)


//                        Glide.with(ivProgram)
//                            .asBitmap()
//                            .load(model.streamIcon)
//                            .override(100, 150)
//                            .into(object : CustomTarget<Bitmap>() {
//                                override fun onResourceReady(
//                                    resource: Bitmap,
//                                    transition: Transition<in Bitmap>?
//                                ) {
//                                    ivProgram.setImageBitmap(resource)
//                                }
//
//                                override fun onLoadCleared(placeholder: Drawable?) {
//                                    // this is called when imageView is cleared on lifecycle call or for
//                                    // some other reason.
//                                    // if you are referencing the bitmap somewhere else too other than this imageView
//                                    // clear it here as you can no longer have the bitmap
//                                }
//                            })


//                        CoroutineScope(Dispatchers.Main).launch {
//                            Glide.with(ivProgram.context)
//                                .load(model.streamIcon)
//                                .transition(DrawableTransitionOptions.withCrossFade())
//                                .override(100, 150)
//                                .into(ivProgram)
//                        }
//
//
//                        imageLoad(ivProgram, model.streamIcon) { bitmap, imageView ->
//                            imageView.setImageBitmap(bitmap)
//                        }


//                        CoroutineScope(Dispatchers.IO).launch {
//                            Glide.with(ivProgram.context)
//                                .asBitmap()
//                                .load(model.streamIcon)
//                                .into(object : CustomTarget<Bitmap>(){
//                                    override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
//                                        withContext(Dispatchers.Main){
//
//                                        }
//                                        imageView.setImageBitmap(resource)
//                                    }
//                                    override fun onLoadCleared(placeholder: Drawable?) {
//                                        // this is called when imageView is cleared on lifecycle call or for
//                                        // some other reason.
//                                        // if you are referencing the bitmap somewhere else too other than this imageView
//                                        // clear it here as you can no longer have the bitmap
//                                    }
//                                })
//
//
//
////                            val myBitmap = BitmapFactory.decodeFile(model.streamIcon)
////                            withContext(Dispatchers.Main) {
////                                ivProgram.setImageBitmap(myBitmap)
////                            }
//                        }
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }

                itemView.setOnClickListener {
                    it.context.apply {
                        clickListener.onClick(it, absoluteAdapterPosition, model, -2)
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

                itemView.setOnLongClickListener {
                    manageClick(it, absoluteAdapterPosition)
                    false
                }

            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    private fun imageLoad(
        imageView: ImageView,
        url: String,
        callback: (Bitmap, ImageView) -> Unit
    ) {
        CoroutineScope(Dispatchers.Default).launch {
            Glide.with(imageView)
                .asBitmap()
                .load(url)
                .override(100, 150)
                .into(object : CustomTarget<Bitmap>() {
                    override fun onResourceReady(
                        resource: Bitmap,
                        transition: Transition<in Bitmap>?
                    ) {
                        (imageView.context as Activity).runOnUiThread {
                            imageView.setImageBitmap(resource)
                        }

                        // callback.invoke(resource, imageView)
                    }

                    override fun onLoadCleared(placeholder: Drawable?) {
                        // this is called when imageView is cleared on lifecycle call or for
                        // some other reason.
                        // if you are referencing the bitmap somewhere else too other than this imageView
                        // clear it here as you can no longer have the bitmap
                    }
                })
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
        //  popupWindow?.showAsDropDown(view, 0, -20)

        val model = getItem(absoluteAdapterPosition)
        if (model!!.isFav) {
            binding.tvAddToFav.setText(R.string.remove_from_favourite)
        }
        binding.ivPlay.setImageResource(R.drawable.movie_details_icon)
        binding.tvPlay.setText(R.string.movie_info)


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
    ): MovieProgramGridViewPagingAdapter.ViewHolder {
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

    object ItemComparator : DiffUtil.ItemCallback<VideoStreamCategory>() {
        override fun areItemsTheSame(
            oldItem: VideoStreamCategory,
            newItem: VideoStreamCategory
        ): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(
            oldItem: VideoStreamCategory,
            newItem: VideoStreamCategory
        ): Boolean {
            return oldItem == newItem
        }
    }

}