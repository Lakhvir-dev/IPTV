package com.dhiman.iptv.dialog.loading_dialog

import android.annotation.SuppressLint
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
import com.dhiman.iptv.data.model.UserModel
import com.dhiman.iptv.databinding.DialogLoadingBinding
import com.dhiman.iptv.util.ConstantUtil
import com.dhiman.iptv.util.DateFormatUtils
import com.dhiman.iptv.util.Status
import com.dhiman.iptv.util.manageDialog
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*
import javax.inject.Inject

@AndroidEntryPoint
class LoadingDialog : DialogFragment() {

    @Inject
    lateinit var sharedPrefs: SharedPrefs
    var callBack: (() -> Unit)? = null
    private lateinit var request: Job
    private lateinit var currentUserModel: UserModel
    private val viewModel: LoadingDialogViewModel by viewModels()
    private var progressCount = 0

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        super.onCreateDialog(savedInstanceState)
        val builder = AlertDialog.Builder(requireContext(), R.style.CustomDialogTheme)
        val binding = DialogLoadingBinding.inflate(layoutInflater, null, false)
        builder.setView(binding.root)
        binding.viewModel = viewModel
        val dialog = builder.create()
        dialog.setCancelable(false)
        dialog.setCanceledOnTouchOutside(false)
        requireActivity().manageDialog(dialog)
        request = repeatFun(binding)

        deleteAllCategoriesFromRoomDatabase()
        getAllCategoriesAsPerLogin()
        setupObserver()

        return dialog
    }

    /** Delete All Categories From Room Database */
    private fun deleteAllCategoriesFromRoomDatabase() {
        viewModel.deleteAllCategoriesFromRoomDatabase()
    }

    /** Fetch All Live Categories */
    private fun getAllCategoriesAsPerLogin() {
        currentUserModel = sharedPrefs.getCurrentUser()
       // if (currentUserModel.playListType == ConstantUtil.XTREAM_URL) {
              viewModel.getAllTypeCategories(currentUserModel)
            //viewModel.allApiCall(currentUserModel)
            //   viewModel.getAllTypeCategory(currentUserModel)
       // }
    }

    /** Observer Listeners */
    private fun setupObserver() {
        viewModel.liveCategoriesData.observe(this) {
            when (it.status) {
                Status.SUCCESS -> {
                    viewModel.insertAllLiveCategoriesToRoomDatabase(it.data)
                }
                Status.LOADING -> {

                }
                Status.ERROR -> {
                    dismiss()
                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_LONG).show()
                }
            }
        }

        viewModel.videoCategoriesData.observe(this) {
            when (it.status) {
                Status.SUCCESS -> {
                    viewModel.insertAllVideoCategoriesToRoomDatabase(it.data)
                }
                Status.LOADING -> {

                }
                Status.ERROR -> {
                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_LONG).show()
                }
            }
        }

        viewModel.seriesCategoriesData.observe(this) {
            when (it.status) {
                Status.SUCCESS -> {
                    viewModel.insertAllSeriesCategoriesToRoomDatabase(it.data)
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

        viewModel.videoStreamCategoriesData.observe(this) {
            when (it.status) {
                Status.SUCCESS -> {
                    viewModel.insertAllVideoStreamCategoriesToRoomDatabase(it.data)
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

                }
                Status.ERROR -> {
                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_LONG).show()
                }
            }
        }

        viewModel.seriesStreamCategoriesData.observe(this) {
            when (it.status) {
                Status.SUCCESS -> {
                    viewModel.insertAllSeriesStreamCategoriesToRoomDatabase(it.data)
                }
                Status.LOADING -> {

                }
                Status.ERROR -> {
                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun repeatFun(binding: DialogLoadingBinding): Job {
        return CoroutineScope(Dispatchers.Main).launch {
            while (isActive) {
                delay(300)
                binding.progressBar.progress = progressCount
                if (progressCount < 100) {
                    progressCount = progressCount.plus(1)
                    binding.tvProgress.text = "$progressCount %"
                } else {
                    request.cancel()
                    dismiss()
                    sharedPrefs.save(
                        ConstantUtil.CHANNEL_REFRESH_DATE,
                        DateFormatUtils.todayStartTime()
                    )
                //    callBack?.invoke()
                }
            }
        }
    }

}