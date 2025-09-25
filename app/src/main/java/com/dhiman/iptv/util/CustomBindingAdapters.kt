package com.dhiman.iptv.util

import android.graphics.drawable.Drawable
import android.view.View
import androidx.databinding.BindingAdapter

@BindingAdapter(value = ["selector_background", "request_focus"], requireAll = false)
fun setSelectorBackground(view: View, value: Drawable?, requestFocus: Boolean? = false) {
    view.isFocusable = true
    view.isFocusableInTouchMode = true
    view.isClickable = true
    view.background = value

    if (requestFocus == true) {
        view.post {
            view.requestFocus()
        }
    }
}