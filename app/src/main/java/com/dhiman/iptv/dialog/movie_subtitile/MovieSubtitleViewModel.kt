package com.dhiman.iptv.dialog.movie_subtitile

import android.view.View
import androidx.databinding.ObservableField
import com.dhiman.iptv.R
import com.dhiman.iptv.util.base.BaseViewModel

class MovieSubtitleViewModel : BaseViewModel() {

    val isFocusOnBack = ObservableField(true)
    val isFocusOnEnglish = ObservableField(true)
    val isFocusOnArabic = ObservableField(false)
    val isFocusOnTurkish = ObservableField(false)

    fun onFocusChange(view: View, value: Boolean) {
        when (view.id) {
            R.id.ivCross -> {
                isFocusOnBack.set(value)
            }

            R.id.etEnglish -> {
                isFocusOnEnglish.set(value)
            }

            R.id.etArabic -> {
                isFocusOnArabic.set(value)
            }
            R.id.etTurkish -> {
                isFocusOnTurkish.set(value)
            }
        }

    }

    fun onClick(view: View) {
        when (view.id) {
            R.id.etEnglish -> {
                isFocusOnEnglish.set(true)
                isFocusOnArabic.set(false)
                isFocusOnTurkish.set(false)
            }

            R.id.etArabic -> {
                isFocusOnEnglish.set(false)
                isFocusOnArabic.set(true)
                isFocusOnTurkish.set(false)
            }

            R.id.etTurkish -> {
                isFocusOnEnglish.set(false)
                isFocusOnArabic.set(false)
                isFocusOnTurkish.set(true)
            }
        }
    }
}