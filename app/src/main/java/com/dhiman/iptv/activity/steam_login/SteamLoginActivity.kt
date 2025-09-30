package com.dhiman.iptv.activity.steam_login

import android.content.Intent
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import androidx.activity.viewModels
import com.dhiman.iptv.activity.BaseActivity
import com.dhiman.iptv.activity.dashboard.HomeActivity
import com.dhiman.iptv.activity.user_list.UserListActivity
import com.dhiman.iptv.data.local.prefs.SharedPrefs
import com.dhiman.iptv.data.model.UserModel
import com.dhiman.iptv.databinding.ActivitySteamLoginBinding
import com.dhiman.iptv.dialog.add_epg.AddEpgViewModel
import com.dhiman.iptv.util.ConstantUtil
import com.dhiman.iptv.util.DateFormatUtils
import com.dhiman.iptv.util.ProgressDialogPref
import com.dhiman.iptv.util.Status
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import kotlin.getValue
import kotlin.toString

@AndroidEntryPoint
class SteamLoginActivity :
    BaseActivity<ActivitySteamLoginBinding>(ActivitySteamLoginBinding::inflate) {
    private val viewModel: AddEpgViewModel by viewModels()
    @Inject
    lateinit var sharedPrefs: SharedPrefs

    override fun onActivityReady() {
        binding.apply {
            btnLogin.setOnClickListener {
               startActivity(Intent(this@SteamLoginActivity, HomeActivity::class.java))

                  /* if (binding.etUrl.text.toString().trim().isEmpty() ||
                       binding.usernameEditText.text.toString().trim().isEmpty() ||
                       binding.passwordEditText.text.toString().trim().isEmpty()
                   ) {
                       Toast.makeText(it.context, "Enter all details first", Toast.LENGTH_SHORT).show()
                   } else {
                       viewModel.userAuth(
                           binding.etUrl.text.toString(),
                           binding.usernameEditText.text.toString(),
                           binding.passwordEditText.text.toString()
                       )
                   }*/
            }

            llUserList.setOnClickListener {
                startActivity(Intent(this@SteamLoginActivity, UserListActivity::class.java))
            }
        }
        setupObserver()
    }

    private fun setupObserver() {
        viewModel.userAuth.observe(this) {
            when (it.status) {
                Status.SUCCESS -> {
                    ProgressDialogPref.hideLoader()
                    addM3uUrl()
                }
                Status.LOADING -> {
                    ProgressDialogPref.showLoader(this@SteamLoginActivity)
                }
                Status.ERROR -> {
                    ProgressDialogPref.hideLoader()
                    Toast.makeText(this@SteamLoginActivity, it.message, Toast.LENGTH_LONG).show()
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
                    Toast.makeText(this@SteamLoginActivity, it.message, Toast.LENGTH_LONG).show()
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
                    Toast.makeText(this@SteamLoginActivity, it.message, Toast.LENGTH_LONG).show()
                }
            }
        }

        viewModel.epgData.observe(this) {
            when (it.status) {
                Status.SUCCESS -> {
                    ProgressDialogPref.hideLoader()
                    viewModel.insertAllEpgToRoomDatabase(it.data)

                    Handler(Looper.getMainLooper()).postDelayed({
                        sharedPrefs.save(
                            ConstantUtil.CHANNEL_REFRESH_DATE,
                            DateFormatUtils.todayStartTime()
                        )
                        startActivity(Intent(this@SteamLoginActivity, HomeActivity::class.java))

                    }, 3000)
                }
                Status.LOADING -> {
                    ProgressDialogPref.showLoader(this@SteamLoginActivity)
                }
                Status.ERROR -> {
                    ProgressDialogPref.hideLoader()
                    Toast.makeText(this@SteamLoginActivity, it.message, Toast.LENGTH_LONG).show()
                }
            }
        }

    }
    /** Add M3U8 URL */
    private fun addM3uUrl() {
        try {
            val userModel = sharedPrefs.getCurrentUser()
            userModel.mainUrl = binding.etUrl.text.toString()
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