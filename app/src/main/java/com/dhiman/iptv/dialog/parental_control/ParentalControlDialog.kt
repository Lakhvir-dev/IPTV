package com.dhiman.iptv.dialog.parental_control

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import com.dhiman.iptv.R
import com.dhiman.iptv.fragment.setting.parental_control.SetupPinViewModel
import com.dhiman.iptv.data.local.prefs.SharedPrefs
import com.dhiman.iptv.databinding.ActivitySetupPinBinding
import com.dhiman.iptv.util.ConstantUtil
import com.dhiman.iptv.util.longToast
import com.dhiman.iptv.util.manageDialog
import com.dhiman.iptv.util.shortToast
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ParentalControlDialog : DialogFragment() {

    @Inject
    lateinit var sharedPrefs: SharedPrefs
    var callBack: (() -> Unit)? = null
    private val viewModel: SetupPinViewModel by viewModels()

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        super.onCreateDialog(savedInstanceState)
        val builder = AlertDialog.Builder(requireContext(), R.style.CustomDialogTheme)
        val binding = ActivitySetupPinBinding.inflate(layoutInflater, null, false)
        builder.setView(binding.root)
        binding.viewModel = viewModel
        val dialog = builder.create()
        requireActivity().manageDialog(dialog)

        /*binding.ivCross.setOnClickListener {
            dismiss()
        }

        binding.btnCancel.setOnClickListener {
            dismiss()
        }*/

        binding.btnSave.setOnClickListener {
            val passwordText = binding.etSetupPin.text.toString()
            val confirmPasswordText = binding.etReEnterPin.text.toString()

            if (passwordText.trim().isEmpty()) {
                "Enter Password".shortToast(requireContext())
            } else if (passwordText.length < 3) {
                "Enter at least 3 letter in Password".shortToast(requireContext())
            } else if (confirmPasswordText.trim().isEmpty()) {
                "Enter Confirm Password".shortToast(requireContext())
            } else if (confirmPasswordText != passwordText) {
                "Password & Confirm Password are not same".shortToast(requireContext())
            } else {
                sharedPrefs.save(ConstantUtil.PARENTAL_CONTROL_PASSWORD, passwordText)
                sharedPrefs.save(ConstantUtil.IS_PARENTAL_CONTROL, true)
                "Password Set Successfully".longToast(requireContext())
                dismiss()
            }

//            dismiss()
//            callBack?.invoke()
        }

        return dialog
    }


}