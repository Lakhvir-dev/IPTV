package com.dhiman.iptv.activity.recording

import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.ObservableField
import com.dhiman.iptv.R
import com.dhiman.iptv.util.base.BaseViewModel

class RecordingListViewModel : BaseViewModel() {
    lateinit var adapter: RecordingListAdapter
    val isFocusOnBack = ObservableField(false)

    fun onFocusChange(view: View, value: Boolean) {
        when (view.id) {
            R.id.ivBack -> {
                isFocusOnBack.set(value)
            }
        }
    }

    init {
        manageAdapter()
    }

    private fun manageAdapter() {
        adapter = RecordingListAdapter()
    }

    fun onClick(view: View) {
        when (view.id) {
            R.id.ivBack -> {
                (view.context as AppCompatActivity).finish()
            }
        }
    }
}