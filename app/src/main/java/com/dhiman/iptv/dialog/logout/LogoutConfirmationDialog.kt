package com.dhiman.iptv.dialog.logout

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.dhiman.iptv.R
import com.dhiman.iptv.databinding.*
import com.dhiman.iptv.util.manageDialog

class LogoutConfirmationDialog : DialogFragment() {
    var callBack: (() -> Unit)? = null

    private val viewModel by lazy {
        LogoutConfirmationViewModel()
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        super.onCreateDialog(savedInstanceState)
        val builder = AlertDialog.Builder(requireContext(), R.style.CustomDialogTheme)
        val binding = DialogLogoutConfirmationBinding.inflate(layoutInflater, null, false)
        builder.setView(binding.root)
        binding.viewModel = viewModel
        val dialog = builder.create()
        requireActivity().manageDialog(dialog)

        binding.ivCross.setOnClickListener {
            dismiss()
        }

        binding.btnCancel.setOnClickListener {
            dismiss()
        }

        binding.btnSave.setOnClickListener {
            dismiss()
            callBack?.invoke()
        }

        return dialog
    }

}