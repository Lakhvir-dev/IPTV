package com.dhiman.iptv.dialog.automation

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import com.dhiman.iptv.R
import com.dhiman.iptv.data.local.prefs.SharedPrefs
import com.dhiman.iptv.databinding.DialogAutomationBinding
import com.dhiman.iptv.util.ConstantUtil
import com.dhiman.iptv.util.manageDialog
import com.dhiman.iptv.util.shortToast
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class AutomationDialog : DialogFragment() {

    @Inject
    lateinit var sharedPrefs: SharedPrefs
    var callBack: (() -> Unit)? = null
    private val viewModel: AutomationViewModel by viewModels()

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        super.onCreateDialog(savedInstanceState)
        val builder = AlertDialog.Builder(requireContext(), R.style.CustomDialogTheme)
        val binding = DialogAutomationBinding.inflate(layoutInflater, null, false)
        builder.setView(binding.root)
        binding.viewModel = viewModel
        val dialog = builder.create()
        requireActivity().manageDialog(dialog)

        val isAutoUpdateChannelMovies =
            sharedPrefs.getBoolean(ConstantUtil.IS_AUTO_UPDATE_CHANNEL_MOVIE)
        val isAutoUpdateEpg = sharedPrefs.getBoolean(ConstantUtil.IS_AUTO_UPDATE_EPG)
        viewModel.isEpgAutoUpdate.postValue(isAutoUpdateEpg)
        viewModel.isChannelMovieAutoUpdate.postValue(isAutoUpdateChannelMovies)

        binding.etAutoUpdateChannel.keyListener = null
        binding.etAutoUpdateEpg.keyListener = null

        binding.ivCross.setOnClickListener {
            dismiss()
        }
        binding.btnCancel.setOnClickListener {
            dismiss()
        }

        binding.btnSave.setOnClickListener {
            viewModel.isChannelMovieAutoUpdate.value?.let { value ->
                sharedPrefs.save(
                    ConstantUtil.IS_AUTO_UPDATE_CHANNEL_MOVIE,
                    value
                )
            }
            viewModel.isEpgAutoUpdate.value?.let { value ->
                sharedPrefs.save(
                    ConstantUtil.IS_AUTO_UPDATE_EPG,
                    value
                )
            }
            "Preferences Updated".shortToast(requireContext())
            dismiss()
            // callBack?.invoke()
        }

        setupObserver(binding)

        return dialog
    }

    /** Observer Listeners */
    private fun setupObserver(binding: DialogAutomationBinding) {
        viewModel.isChannelMovieAutoUpdate.observe(this) {
            if (it) {
                binding.etAutoUpdateChannel.setCompoundDrawablesWithIntrinsicBounds(
                    R.drawable.ic_white_check_svg,
                    0,
                    0,
                    0
                )
            } else {
                binding.etAutoUpdateChannel.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0)
            }
        }

        viewModel.isEpgAutoUpdate.observe(this) {
            if (it) {
                binding.etAutoUpdateEpg.setCompoundDrawablesWithIntrinsicBounds(
                    R.drawable.ic_white_check_svg,
                    0,
                    0,
                    0
                )
            } else {
                binding.etAutoUpdateEpg.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0)
            }
        }
    }


}