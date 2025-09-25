package com.dhiman.iptv.dialog.progressDialog

import android.app.AlertDialog
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentActivity
import com.dhiman.iptv.databinding.DialogProgressLoaderBinding

class ProgressDialogLoader : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        super.onCreateDialog(savedInstanceState)
        val builder = AlertDialog.Builder(requireContext())
        val binding = DialogProgressLoaderBinding.inflate(layoutInflater, null, false)
        builder.setView(binding.root)
        val dialog = builder.create()
        dialog.window!!.setBackgroundDrawable(
            ColorDrawable(Color.TRANSPARENT)
        )
        dialog.setCancelable(false)
        dialog.setCanceledOnTouchOutside(false)

        return dialog
    }

    fun showLoader(requireActivity: FragmentActivity) {
        show(requireActivity.supportFragmentManager, "")
    }

    fun hideLoader() {
        dismiss()
    }

}