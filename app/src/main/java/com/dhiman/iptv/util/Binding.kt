package com.dhiman.iptv.util

import android.view.View
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView

@BindingAdapter("adapter", requireAll = false)
fun RecyclerView.recyclerViewAdapter(adapters: RecyclerView.Adapter<*>) {
    adapter = adapters
}

@BindingAdapter("app:onFocusChange", requireAll = false)
fun View.onFocusChange(onFocusChangeListenerInterface: OnFocusChangeListenerInterface) {
    onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
        onFocusChangeListenerInterface.onFocus(this, hasFocus)
        /*if (hasFocus) {
            onFocusChangeListenerInterface.onFocus(this, hasFocus)
        }*/
    }

}

interface OnFocusChangeListenerInterface {
    fun onFocus(view: View, value: Boolean)
}