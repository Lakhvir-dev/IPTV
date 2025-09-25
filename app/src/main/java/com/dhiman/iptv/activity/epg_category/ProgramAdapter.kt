package com.dhiman.iptv.activity.epg_category

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.dhiman.iptv.R

class ProgramAdapter(private val programs: List<Program>) :
    RecyclerView.Adapter<ProgramAdapter.ProgramViewHolder>() {

    companion object {
        const val MINUTE_WIDTH_DP = 4   // width per minute
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProgramViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_program, parent, false)
        return ProgramViewHolder(view)
    }

    override fun getItemCount() = programs.size

    override fun onBindViewHolder(holder: ProgramViewHolder, position: Int) {
        val program = programs[position]
        holder.bind(program)
    }

    inner class ProgramViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val title = itemView.findViewById<TextView>(R.id.programTitle)

        fun bind(program: Program) {
            title.text = program.title

            // calculate width
            val duration = program.endMinutes - program.startMinutes
            val widthPx = dpToPx(itemView.context, duration * MINUTE_WIDTH_DP)

            title.layoutParams = RecyclerView.LayoutParams(widthPx, ViewGroup.LayoutParams.MATCH_PARENT)
        }
    }

    private fun dpToPx(context: Context, dp: Int): Int {
        return (dp * context.resources.displayMetrics.density).toInt()
    }
}
