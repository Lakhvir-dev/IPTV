package com.dhiman.iptv.activity.live_program_list

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import androidx.recyclerview.widget.RecyclerView
import com.dhiman.iptv.R
import com.dhiman.iptv.util.gone
import com.dhiman.iptv.util.visible

class LiveProgramGridViewNewAdapter(val width: Int?) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.list_item_program_grid_view, parent, false)
        )

    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        holder as ViewHolder
        holder.itemView.setOnClickListener {
            it.context.apply {
                startActivity(Intent(this, LiveProgramListActivity::class.java))
            }
        }
        manageWidthHeight(holder)
    }

    private fun manageWidthHeight(viewHolder: ViewHolder) {
        val viewWidth = width?.div(3)
        val lp: ViewGroup.MarginLayoutParams =
            viewHolder.itemView.layoutParams as ViewGroup.MarginLayoutParams
        val marginStart = lp.marginStart
        val marginEnd = lp.marginEnd
        val totalMargin = marginStart + marginEnd
        viewHolder.itemView.layoutParams = LinearLayout.LayoutParams(
            viewWidth?.minus(totalMargin.plus(20))!!,
            viewWidth.minus(totalMargin)
        )

    }

    override fun getItemCount(): Int {
        return 20
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val rlFavourite: RelativeLayout = view.findViewById(R.id.rlFavourite)
        val ivFav: ImageView = view.findViewById(R.id.ivFav)
        val ivProgram: ImageView = view.findViewById(R.id.ivProgram)
        val ivFavFill: ImageView = view.findViewById(R.id.ivFavFill)

        init {
            rlFavourite.setOnClickListener {
                if (ivFav.visibility == View.VISIBLE) {
                    ivFav.gone()
                    ivFavFill.visible()
                } else {
                    ivFav.visible()
                    ivFavFill.gone()
                }
            }
        }
    }
}