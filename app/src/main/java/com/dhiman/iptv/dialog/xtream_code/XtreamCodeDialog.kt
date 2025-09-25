package com.dhiman.iptv.dialog.xtream_code

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import com.dhiman.iptv.R
import com.dhiman.iptv.data.local.prefs.SharedPrefs
import com.dhiman.iptv.data.model.UserModel
import com.dhiman.iptv.databinding.DialogStreamCodeBinding
import com.dhiman.iptv.util.ConstantUtil
import com.dhiman.iptv.util.ProgressDialogPref
import com.dhiman.iptv.util.Status
import com.dhiman.iptv.util.manageDialog
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class XtreamCodeDialog : DialogFragment() {

    @Inject
    lateinit var sharedPrefs: SharedPrefs
    var callBack: (() -> Unit)? = null
    private val viewModel: XtreamCodeViewModel by viewModels()
    lateinit var binding: DialogStreamCodeBinding

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        super.onCreateDialog(savedInstanceState)
        val builder = AlertDialog.Builder(requireContext(), R.style.CustomDialogTheme)
        binding = DialogStreamCodeBinding.inflate(layoutInflater, null, false)
        builder.setView(binding.root)
        binding.viewModel = viewModel

        val dialog = builder.create()
        requireActivity().manageDialog(dialog)
        binding.ivCross.setOnClickListener {
            dismiss()
        }

        binding.btnLogin.setOnClickListener {
            if (binding.urlEditText.text.toString().trim().isEmpty() ||
                binding.usernameEditText.text.toString().trim().isEmpty() ||
                binding.passwordEditText.text.toString().trim().isEmpty()
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
        viewModel._userAuth.observe(this) {
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
    }

    /** Add M3U8 URL */
    private fun addM3uUrl() {
        try {
            val userList = sharedPrefs.loadUserList()

            val url = binding.urlEditText.text.toString() +
                    "/player_api.php?username=" +
                    binding.usernameEditText.text.toString() +
                    "&password=" +
                    binding.passwordEditText.text.toString()
            val userModel = UserModel()
            userModel.id = userList.size
            userModel.mainUrl = binding.urlEditText.text.toString()
            userModel.userName = binding.usernameEditText.text.toString()
            userModel.password = binding.passwordEditText.text.toString()
            userModel.playListName = "Test"
            userModel.playListType = ConstantUtil.XTREAM_URL
            userModel.url = url
            userList.add(userModel)
            sharedPrefs.saveUserToList(userList)
            sharedPrefs.saveUser(userModel)
            dismiss()

            callBack?.invoke()
        } catch (e: NullPointerException) {
            e.printStackTrace()
        }

    }

}