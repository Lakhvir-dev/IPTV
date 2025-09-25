package com.dhiman.iptv.dialog.add_play_list

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import com.dhiman.iptv.R
import com.dhiman.iptv.data.local.db.entity.UserDataModel
import com.dhiman.iptv.data.local.prefs.SharedPrefs
import com.dhiman.iptv.data.model.UserModel
import com.dhiman.iptv.databinding.DialogPlayListBinding
import com.dhiman.iptv.util.*
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class AddPlayListDialog : DialogFragment() {

    @Inject
    lateinit var sharedPrefs: SharedPrefs
    var callBack: (() -> Unit)? = null
    private val viewModel: AddPlayListViewModel by viewModels()
    lateinit var binding: DialogPlayListBinding

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        super.onCreateDialog(savedInstanceState)
        val builder = AlertDialog.Builder(requireContext(), R.style.CustomDialogTheme)
        binding = DialogPlayListBinding.inflate(layoutInflater, null, false)
        builder.setView(binding.root)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        val dialog = builder.create()

        requireActivity().manageDialog(dialog)

        binding.tilUploadFile.setEndIconOnClickListener {
            viewModel.isUploadedFileShowing.set(false)
        }
        binding.etFileName.keyListener = null
        binding.ivCross.setOnClickListener {
            dismiss()
        }
        //  binding.etFileUrlName.setText("http://toh-users.xyz:8080/get.php?username=apdev101&password=101apdev&type=m3u&output=mpegts")

        /** Radio Button Listeners */
        radioButtonListeners()

        binding.btnAdd.setOnClickListener {
            if (binding.rbFile.isChecked) {
                if (binding.playlistNameEditText.text.toString().trim().isEmpty()) {
                    "Enter Playlist Name".shortToast(requireActivity())
                } else if (viewModel.filePath.value?.trim()?.isNotEmpty() == true &&
                    viewModel.isUploadedFileShowing.get() == true
                ) {
                    ProgressDialogPref.showLoader(requireActivity())
                    checkAndAddLocalFile()
                } else {
                    "Select File First".shortToast(requireActivity())
                }
            } else {
                if (binding.playlistNameEditText.text.toString().trim().isEmpty()) {
                    "Enter Playlist Name".shortToast(requireActivity())
                } else if (binding.etFileUrlName.text.toString().trim().isEmpty()) {
                    "Please Enter Url".shortToast(requireContext())
                } else {
                    ProgressDialogPref.showLoader(requireActivity())
                    viewModel.downloadFile(binding.etFileUrlName.text.toString())
                    { filePath, isSuccess ->
                        if (isSuccess) {
                            viewModel.filePath.value = filePath
                            checkAndAddLocalFile()
                        } else {
                            ProgressDialogPref.hideLoader()
                            filePath.shortToast(requireActivity())
                        }
                    }
                }
            }
        }
        return dialog
    }

    /**
     * Radio Button Listeners
     */
    private fun radioButtonListeners() {
        binding.apply {
            rbFile.setOnCheckedChangeListener { buttonView, isChecked ->
                if (isChecked) {
                    tilUrlFile.gone()
                    llUploadFile.visible()
                    viewModel?.isUploadedFileShowing?.set(false)
                } else {
                    tilUrlFile.visible()
                    tilUploadFile.gone()
                    llUploadFile.gone()
                    viewModel?.isUploadedFileShowing?.set(false)
                }
            }
        }
    }

    /** Check file has valid data & Add data to Shared Preferences */
    private fun checkAndAddLocalFile() {
        if (ParseFile.checkFileContainUrl(viewModel.filePath.value.toString())) {
            try {
                val userList = sharedPrefs.loadUserList()
                val userModel = UserModel()
                userModel.id = userList.size
                userModel.playListName = binding.playlistNameEditText.text.toString()
                userModel.playListType = ConstantUtil.FILE
                userModel.url = viewModel.filePath.value.toString()
                userList.add(userModel)
                sharedPrefs.saveUserToList(userList)
                //  sharedPrefs.saveUser(userModel)

                val userDataModel = UserDataModel(userModel = userModel)
                viewModel.addUserToRoomDatabase(userDataModel)
                "User Added Successfully".shortToast(requireActivity())

                ProgressDialogPref.hideLoader()
                dismiss()
                callBack?.invoke()
            } catch (e: NullPointerException) {
                e.printStackTrace()
            }
        } else {
            getString(R.string.error_unable_to_add_user).longToast(requireActivity())
        }
    }


}