package com.dhiman.iptv.util

import android.view.View

interface RecyclerViewClickListener {

    fun onClick(view: View, position: Int, selectedModel: Any, childPosition: Int)

}