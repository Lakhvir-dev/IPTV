package com.dhiman.iptv.dialog.multiScreenResource

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.fragment.app.DialogFragment
import com.dhiman.iptv.R
import com.dhiman.iptv.databinding.*
import com.dhiman.iptv.util.ConstantUtil
import com.dhiman.iptv.util.manageDialog

class MultiScreenResourceDialog : DialogFragment() {
    var callBack: ((String) -> Unit)? = null
    var dismissCallBack: (() -> Unit)? = null

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        super.onCreateDialog(savedInstanceState)
        val builder = AlertDialog.Builder(requireContext(), R.style.CustomDialogTheme)
        val binding = DialogMultiScreenResourceBinding.inflate(layoutInflater, null, false)
        builder.setView(binding.root)
        val dialog = builder.create()
        requireActivity().manageDialog(dialog)

        binding.apply {
            ivCross.setOnClickListener {
                dialog.dismiss()
                dismissCallBack?.invoke()
            }

            ivLive.setOnClickListener {
                dialog.dismiss()
                callBack?.invoke(ConstantUtil.INTENT_LIVE)
            }

            ivMovie.setOnClickListener {
                dialog.dismiss()
                callBack?.invoke(ConstantUtil.INTENT_MOVIES)
            }

            ivSeries.setOnClickListener {
                dialog.dismiss()
                callBack?.invoke(ConstantUtil.INTENT_SERIES)
            }

            /** Setup Focus Change */
            setFocusBackground(ivLive)
            setFocusBackground(ivMovie)
            setFocusBackground(ivSeries)
            setFocusBackground(ivCross)

            ivLive.requestFocus()
            Handler(Looper.getMainLooper()).postDelayed({
                ivLive.requestFocus()
            }, 200)
        }

        return dialog
    }

    /**
     * Setup Focus Change
     */
    private fun setFocusBackground(selectedView: View) {
        selectedView.setOnFocusChangeListener { _, b ->
            if (b) {
                selectedView.setBackgroundResource(R.drawable.select_bg_program_list)
            } else {
                selectedView.setBackgroundResource(R.drawable.transparent_bg)
            }
        }
    }

}