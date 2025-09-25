package com.dhiman.iptv.activity.dashboard

import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.dhiman.iptv.activity.BaseActivity
import com.dhiman.iptv.data.local.db.entity.UserDataModel
import com.dhiman.iptv.data.local.prefs.SharedPrefs
import com.dhiman.iptv.databinding.ActivityHomeBinding
import com.dhiman.iptv.dialog.parental_control.EnterParentalControlDialog
import com.dhiman.iptv.util.ConstantUtil
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class HomeActivity : BaseActivity<ActivityHomeBinding>(ActivityHomeBinding::inflate) {

    @Inject
    lateinit var sharedPrefs: SharedPrefs
    private val viewModel: HomeActivityViewModel by viewModels()

    override fun onActivityReady() {
        binding.viewModel = viewModel
        getDataFromIntent()
    }

    /**
     * Get Data from Intent and setup UI
     */
    private fun getDataFromIntent() {
        val intent = intent
        if (intent.hasExtra(ConstantUtil.INTENT_ID)) {
            val intentType = intent.getStringExtra(ConstantUtil.INTENT_ID).toString()
            if (intentType.equals(ConstantUtil.REFRESH_DATA, true)) {
                viewModel.refreshData(this@HomeActivity)
            } else if (intentType.equals(ConstantUtil.REFRESH_DATA_M3U, true)) {
                val dataModelString = intent.getStringExtra(ConstantUtil.DATA_MODEL).toString()
                val dataModel = Gson().fromJson(dataModelString, UserDataModel::class.java)
                viewModel.refreshLocalData(this@HomeActivity, dataModel.userModel.url.toString())
            }
        }

        /** Check App is Protected with Parental Password */
        checkParentalPassword()
    }

    /**
     * Check App is Protected with Parental Password
     */
    private fun checkParentalPassword() {
        val isPasswordProtected = sharedPrefs.getBoolean(ConstantUtil.IS_PARENTAL_CONTROL)

        if (isPasswordProtected) {
            val enterParentalControlDialog = EnterParentalControlDialog()
            enterParentalControlDialog.show(
                (this as AppCompatActivity).supportFragmentManager,
                ""
            )
            enterParentalControlDialog.isCancelable = false
        }
    }

}