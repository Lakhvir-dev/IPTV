package com.dhiman.iptv.dialog.movie_display

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.dhiman.iptv.R
import com.dhiman.iptv.databinding.DialogMovieDisplayBinding
import com.dhiman.iptv.util.manageDialog

class MovieDisplayDialog : DialogFragment() {
    var callBack: (() -> Unit)? = null

    private val viewModel by lazy {
        MovieDisplayViewModel()
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        super.onCreateDialog(savedInstanceState)
        val builder = AlertDialog.Builder(requireContext(), R.style.CustomDialogTheme)
        val binding = DialogMovieDisplayBinding.inflate(layoutInflater, null, false)
        builder.setView(binding.root)
        binding.viewModel = viewModel
        val dialog = builder.create()
        requireActivity().manageDialog(dialog)

        binding.ivCross.setOnClickListener {
            dismiss()
        }
        binding.btnResume.setOnClickListener {
            dismiss()
        }

        binding.btnStartOver.setOnClickListener {
            dismiss()
            callBack?.invoke()
        }

        return dialog
    }


}