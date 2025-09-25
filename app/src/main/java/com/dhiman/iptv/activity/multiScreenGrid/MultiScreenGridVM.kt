package com.dhiman.iptv.activity.multiScreenGrid

import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.ObservableField
import com.dhiman.iptv.R
import com.dhiman.iptv.util.base.BaseViewModel

class MultiScreenGridVM : BaseViewModel() {

    val isFocusOnBack = ObservableField(true)
    val isFocusOnFirstGrid = ObservableField(false)
    val isFocusOnSecondGrid = ObservableField(false)
    val isFocusOnThirdGrid = ObservableField(false)
    val isFocusOnFourthGrid = ObservableField(false)
    val isFocusOnFifthGrid = ObservableField(false)
    val isFocusOnSixthGrid = ObservableField(false)

    fun onFocusChange(view: View, value: Boolean) {
        when (view.id) {
            R.id.ivBack -> {
                isFocusOnBack.set(value)
            }

            R.id.rlFirstGrid -> {
                isFocusOnFirstGrid.set(value)
            }

            R.id.rlSecondGrid -> {
                isFocusOnSecondGrid.set(value)
            }

            R.id.rlThirdGrid -> {
                isFocusOnThirdGrid.set(value)
            }

            R.id.rlFourthGrid -> {
                isFocusOnFourthGrid.set(value)
            }

            R.id.rlFifthGrid -> {
                isFocusOnFifthGrid.set(value)
            }

            R.id.rlSixthGrid -> {
                isFocusOnSixthGrid.set(value)
            }
        }

    }

    fun onClick(view: View) {
        when (view.id) {
            R.id.ivBack -> {
                (view.context as AppCompatActivity).finish()
            }
        }
    }
}