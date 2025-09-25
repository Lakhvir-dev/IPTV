package com.dhiman.iptv.activity.series_program

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.dhiman.iptv.R
import com.dhiman.iptv.data.model.SeriesInfoModel
import com.dhiman.iptv.data.model.UserModel
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy

class SeriesProgramHorizontalListAdapter(
    private val currentUserModel: UserModel,
    private val allEpisodeArrayList: ArrayList<SeriesInfoModel.Episodes>,
    private val callback: (SeriesInfoModel.Episodes, Int) -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item_series_program_horizontal_list, parent, false)
        view.isFocusable = true
        view.isFocusableInTouchMode = true

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        holder as ViewHolder

        holder.apply {
            val model = allEpisodeArrayList[position]
            episodeNameTextView.text = model.title

            try {
                Glide.with(episodeImageView.context)
                    .load(model.info.movieImage)
                    .placeholder(R.drawable.vertical_image_place_holder)
                    .error(R.drawable.vertical_image_place_holder)
                    .override(episodeImageView.width, episodeImageView.height)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(episodeImageView)
            } catch (e: Exception) {
                e.printStackTrace()
            }

            itemView.setOnFocusChangeListener { _, isFocused ->
                if (isFocused) {
                    mainRelativeLayout.setBackgroundResource(R.drawable.select_bg_program_list)
                } else {
                    mainRelativeLayout.setBackgroundResource(R.drawable.bg_program_list)
                }
            }

            mainRelativeLayout.setOnClickListener {
                callback.invoke(model, position)
//                val dataArrayList = ArrayList<String>()
//                for (x in 0 until allEpisodeArrayList.size) {
//                    val seriesModel = allEpisodeArrayList[x]
//                    val movieUrl =
//                        currentUserModel.mainUrl + "/series/" +
//                                currentUserModel.userName + "/" +
//                                currentUserModel.password + "/" +
//                                seriesModel.id + "." +
//                                seriesModel.containerExtension
//                    dataArrayList.add(movieUrl)
//                }
//
//                val movieFullUrl =
//                    currentUserModel.mainUrl + "/series/" +
//                            currentUserModel.userName + "/" +
//                            currentUserModel.password + "/" +
//                            model.id + "." +
//                            model.containerExtension
//
//                val intent = Intent(mainRelativeLayout.context, PlayerActivity::class.java)
//                intent.putExtra(ConstantUtil.INTENT_ID, movieFullUrl)
//                intent.putExtra(ConstantUtil.INTENT_TYPE, ConstantUtil.INTENT_SERIES)
//                intent.putExtra(ConstantUtil.INTENT_SELECTED_POS, position)
//                intent.putExtra(ConstantUtil.INTENT_SERIES_EPISODES_LIST, dataArrayList)
//                it.context.startActivity(intent)
            }

        }
    }

    override fun getItemCount(): Int {
        return allEpisodeArrayList.size
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val episodeImageView: ImageView = view.findViewById(R.id.episodeImageView)
        val episodeNameTextView: TextView = view.findViewById(R.id.episodeNameTextView)
        val mainRelativeLayout: RelativeLayout = view.findViewById(R.id.mainRelativeLayout)
    }

}