package com.dhiman.iptv.dialog.parental_control

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.dhiman.iptv.R
import com.dhiman.iptv.data.local.prefs.SharedPrefs
import com.dhiman.iptv.databinding.DialogEnterParentalControlBinding
import com.dhiman.iptv.util.ConstantUtil
import com.dhiman.iptv.util.manageDialog
import com.dhiman.iptv.util.shortToast
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class EnterParentalControlDialog : DialogFragment() {

    @Inject
    lateinit var sharedPrefs: SharedPrefs
    var callBack: (() -> Unit)? = null

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        super.onCreateDialog(savedInstanceState)
        val builder = AlertDialog.Builder(requireContext(), R.style.CustomDialogTheme)
        val binding = DialogEnterParentalControlBinding.inflate(layoutInflater, null, false)
        builder.setView(binding.root)

        val dialog = builder.create()
        dialog.setCancelable(false)
        dialog.setCanceledOnTouchOutside(false)
        requireActivity().manageDialog(dialog)

        val parentalPassword = sharedPrefs.getString(ConstantUtil.PARENTAL_CONTROL_PASSWORD)

        binding.btnSubmit.setOnClickListener {
            val passwordText = binding.enterPasswordEditText.text.toString()
            if (passwordText.trim().isEmpty()) {
                "Enter Password".shortToast(requireContext())
            } else if (passwordText != parentalPassword) {
                "Enter Correct Password".shortToast(requireContext())
            } else {
                "Success".shortToast(requireContext())
                dismiss()
            }
        }
        return dialog
    }

}