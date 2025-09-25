package com.dhiman.iptv.util.custom_view

import android.content.Context
import android.util.AttributeSet

class HelveticaMediumTextView(context: Context, attrs: AttributeSet?) : androidx.appcompat.widget.AppCompatTextView(context, attrs) {
    init {
        typeface = TypeFaceProvider.getTypeFace(context, "fonts/helvetica-neue-medium.ttf")
    }
}