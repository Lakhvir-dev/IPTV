package com.dhiman.iptv.dialog.time_format

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import com.dhiman.iptv.R
import com.dhiman.iptv.data.local.prefs.SharedPrefs
import com.dhiman.iptv.databinding.DialogTimeFormatBinding
import com.dhiman.iptv.util.ConstantUtil
import com.dhiman.iptv.util.manageDialog
import com.dhiman.iptv.util.shortToast
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class TimeFormatDialog : DialogFragment() {

    @Inject
    lateinit var sharedPrefs: SharedPrefs
    var callBack: (() -> Unit)? = null
    private val viewModel: TimeFormatViewModel by viewModels()

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        super.onCreateDialog(savedInstanceState)
        val builder = AlertDialog.Builder(requireContext(), R.style.CustomDialogTheme)
        val binding = DialogTimeFormatBinding.inflate(layoutInflater, null, false)
        builder.setView(binding.root)
        binding.viewModel = viewModel
        val dialog = builder.create()
        requireActivity().manageDialog(dialog)
        binding.et24Hour.keyListener = null
        binding.et12Hour.keyListener = null

        val is24HoursFormat =
            sharedPrefs.getBoolean(ConstantUtil.IS_24_HOURS_FORMAT)
        viewModel.is24Hours.postValue(is24HoursFormat)

        binding.ivCross.setOnClickListener {
            dismiss()
        }
        binding.btnCancel.setOnClickListener {
            dismiss()
        }

        binding.btnSave.setOnClickListener {
            viewModel.is24Hours.value?.let { value ->
                sharedPrefs.save(
                    ConstantUtil.IS_24_HOURS_FORMAT,
                    value
                )
            }
            "Preferences Updated".shortToast(requireContext())

            dismiss()
            callBack?.invoke()
        }

        setupObserver(binding)
        return dialog
    }

    /** Observer Listeners */
    private fun setupObserver(binding: DialogTimeFormatBinding) {
        viewModel.is24Hours.observe(this) {
            if (it) {
                binding.et24Hour.setCompoundDrawablesWithIntrinsicBounds(
                    R.drawable.ic_white_check_svg,
                    0,
                    0,
                    0
                )
                binding.et12Hour.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0)
            } else {
                binding.et12Hour.setCompoundDrawablesWithIntrinsicBounds(
                    R.drawable.ic_white_check_svg,
                    0,
                    0,
                    0
                )
                binding.et24Hour.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0)
            }
        }
    }

}