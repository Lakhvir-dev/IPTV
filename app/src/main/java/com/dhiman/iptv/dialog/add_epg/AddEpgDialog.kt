package com.dhiman.iptv.dialog.add_epg

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import com.dhiman.iptv.R
import com.dhiman.iptv.data.local.prefs.SharedPrefs
import com.dhiman.iptv.databinding.DialogAddEpgBinding
import com.dhiman.iptv.util.*
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class AddEpgDialog : DialogFragment() {

    var callBack: (() -> Unit)? = null

    @Inject
    lateinit var sharedPrefs: SharedPrefs
    private val viewModel: AddEpgViewModel by viewModels()
    lateinit var binding: DialogAddEpgBinding

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        super.onCreateDialog(savedInstanceState)
        val builder = AlertDialog.Builder(requireContext(), R.style.CustomDialogTheme)
        binding = DialogAddEpgBinding.inflate(layoutInflater, null, false)
        builder.setView(binding.root)
        binding.viewModel = viewModel

        val dialog = builder.create()
        requireActivity().manageDialog(dialog)
        binding.ivCross.setOnClickListener {
            dismiss()
        }

        binding.btnLogin.setOnClickListener {
            if (binding.urlEditText.text.toString().trim().isEmpty() ||
                binding.urlEditText.text.toString().trim().isEmpty() ||
                binding.urlEditText.text.toString().trim().isEmpty()
            ) {
                Toast.makeText(it.context, "Enter all details first", Toast.LENGTH_SHORT).show()
            } else {
                viewModel.userAuth(
                    binding.urlEditText.text.toString(),
                    binding.usernameEditText.text.toString(),
                    binding.passwordEditText.text.toString()
                )
            }
        }
        setupObserver()

        return dialog
    }

    /** Observer Listeners */
    private fun setupObserver() {
        viewModel.userAuth.observe(this) {
            when (it.status) {
                Status.SUCCESS -> {
                    ProgressDialogPref.hideLoader()
                    addM3uUrl()
                }
                Status.LOADING -> {
                    ProgressDialogPref.showLoader(requireActivity())
                }
                Status.ERROR -> {
                    ProgressDialogPref.hideLoader()
                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_LONG).show()
                }
            }
        }

        viewModel.liveCategoriesData.observe(this) {
            when (it.status) {
                Status.SUCCESS -> {
                    viewModel.insertAllLiveCategoriesToRoomDatabase(it.data)
                }
                Status.LOADING -> {

                }
                Status.ERROR -> {
                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_LONG).show()
                }
            }
        }

        viewModel.liveStreamCategoriesData.observe(this) {
            when (it.status) {
                Status.SUCCESS -> {
                    viewModel.insertAllLiveStreamCategoriesToRoomDatabase(it.data)
                }
                Status.LOADING -> {

                }
                Status.ERROR -> {
                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_LONG).show()
                }
            }
        }

        viewModel.epgData.observe(this) {
            when (it.status) {
                Status.SUCCESS -> {
                    ProgressDialogPref.hideLoader()
                    viewModel.insertAllEpgToRoomDatabase(it.data)

                    Handler(Looper.getMainLooper()).postDelayed({
                        dismiss()
                        sharedPrefs.save(
                            ConstantUtil.CHANNEL_REFRESH_DATE,
                            DateFormatUtils.todayStartTime()
                        )
                        callBack?.invoke()
                    }, 3000)
                }
                Status.LOADING -> {
                    ProgressDialogPref.showLoader(requireActivity())
                }
                Status.ERROR -> {
                    ProgressDialogPref.hideLoader()
                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_LONG).show()
                }
            }
        }

    }

    /** Add M3U8 URL */
    private fun addM3uUrl() {
        try {
            val userModel = sharedPrefs.getCurrentUser()
            userModel.mainUrl = binding.urlEditText.text.toString()
            userModel.userName = binding.usernameEditText.text.toString()
            userModel.password = binding.passwordEditText.text.toString()
            sharedPrefs.saveUser(userModel)

            viewModel.deleteAllEPGFromRoomDatabase()
            viewModel.getAllEpg(userModel)
        } catch (e: NullPointerException) {
            e.printStackTrace()
        }
    }

}