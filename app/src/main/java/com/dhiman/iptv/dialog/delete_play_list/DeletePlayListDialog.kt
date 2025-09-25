package com.dhiman.iptv.dialog.delete_play_list

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.dhiman.iptv.R
import com.dhiman.iptv.databinding.DialogDeletePlayListBinding
import com.dhiman.iptv.util.manageDialog

class DeletePlayListDialog : DialogFragment() {
    var callBack: (() -> Unit)? = null

    private val viewModel by lazy {
        DeletePlayListViewModel()
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        super.onCreateDialog(savedInstanceState)
        val builder = AlertDialog.Builder(requireContext(), R.style.CustomDialogTheme)
        val binding = DialogDeletePlayListBinding.inflate(layoutInflater, null, false)
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