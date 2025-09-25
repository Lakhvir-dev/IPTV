package com.dhiman.iptv.util.custom_view

import android.content.Context
import android.util.AttributeSet
import com.google.android.material.textfield.TextInputEditText

class HelveticaMediumTextInputEditTextView(context: Context, attrs: AttributeSet?) : TextInputEditText(context, attrs) {
    init {
        typeface = TypeFaceProvider.getTypeFace(context, "fonts/helvetica-neue-medium.ttf")
    }
}