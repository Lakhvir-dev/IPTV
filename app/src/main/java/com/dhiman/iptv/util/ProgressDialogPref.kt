package com.dhiman.iptv.util

import androidx.fragment.app.FragmentActivity
import com.dhiman.iptv.dialog.progressDialog.ProgressDialogLoader

object ProgressDialogPref {

    private var loaderProgress: ProgressDialogLoader = ProgressDialogLoader()

    fun showLoader(requireActivity: FragmentActivity) {
        loaderProgress.showLoader(requireActivity)
        loaderProgress.isCancelable = false
    }

    fun hideLoader() {
        loaderProgress.hideLoader()
    }

}