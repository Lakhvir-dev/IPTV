package com.dhiman.iptv.dialog.movie_subtitile

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.dhiman.iptv.R
import com.dhiman.iptv.databinding.DialogMovieSubtitleBinding
import com.dhiman.iptv.util.manageDialog

class MovieSubtitleDialog : DialogFragment() {
    var callBack: (() -> Unit)? = null

    private val viewModel by lazy {
        MovieSubtitleViewModel()
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        super.onCreateDialog(savedInstanceState)
        val builder = AlertDialog.Builder(requireContext(), R.style.CustomDialogTheme)
        val binding = DialogMovieSubtitleBinding.inflate(layoutInflater, null, false)
        builder.setView(binding.root)
        binding.viewModel = viewModel
        val dialog = builder.create()
        requireActivity().manageDialog(dialog)
        binding.etEnglish.keyListener = null
        binding.etArabic.keyListener = null
        binding.etTurkish.keyListener = null

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