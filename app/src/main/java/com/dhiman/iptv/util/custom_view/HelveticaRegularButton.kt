package com.dhiman.iptv.util.custom_view

import android.content.Context
import android.util.AttributeSet
import com.google.android.material.button.MaterialButton

class HelveticaRegularButton(context: Context, attrs: AttributeSet?) : MaterialButton(context, attrs) {
    init {
        typeface = TypeFaceProvider.getTypeFace(context, "fonts/helvetica-neue-regular.ttf")
    }
}