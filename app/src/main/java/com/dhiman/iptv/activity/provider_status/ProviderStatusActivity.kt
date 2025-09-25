package com.dhiman.iptv.activity.provider_status

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.dhiman.iptv.R
import com.dhiman.iptv.data.model.UserAuth
import com.dhiman.iptv.databinding.ActivityProviderStatusBinding
import com.dhiman.iptv.util.DateFormatUtils
import com.dhiman.iptv.util.ProgressDialogPref
import com.dhiman.iptv.util.Status
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProviderStatusActivity : AppCompatActivity() {

    lateinit var binding: ActivityProviderStatusBinding
    private val viewModel: ProviderStatusViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(
            this,
            R.layout.activity_provider_status
        )
        binding.viewModel = viewModel

        /** Observer Listeners */
        setupObserver()

        /** Set up onFocus Change Listeners */
        setUpFocusChangeListeners()
    }

    /**
     * Set up onFocus Change Listeners
     */
    private fun setUpFocusChangeListeners() {
        binding.ivBack.requestFocus()
        Handler(Looper.getMainLooper()).postDelayed({
            binding.ivBack.requestFocus()
        }, 500)
    }

    /**
     * Observer Listeners
     */
    private fun setupObserver() {
        viewModel.userAuth.observe(this) {
            when (it.status) {
                Status.SUCCESS -> {
                    ProgressDialogPref.hideLoader()
                    it.data?.let { userAuth ->
                        setDataOnUI(userAuth)
                    }
                }
                Status.LOADING -> {
                    ProgressDialogPref.showLoader(this@ProviderStatusActivity)
                }
                Status.ERROR -> {
                    ProgressDialogPref.hideLoader()
                    Toast.makeText(this@ProviderStatusActivity, it.message, Toast.LENGTH_LONG)
                        .show()
                }
            }
        }
    }

    /**
     * Set Data on UI
     */
    private fun setDataOnUI(userAuth: UserAuth) {
        binding.apply {
            usernameTextView.text = userAuth.userInfo?.username
            playlistStatusTextView.text = userAuth.userInfo?.status
            userAuth.userInfo?.expDate?.let {
                expireDateTextView.text = DateFormatUtils.convertLongToDate(it)
            }

            activeConnectionsTextView.text = userAuth.userInfo?.activeCons
            userAuth.userInfo?.createdAt?.let {
                createdDateTextView.text =
                    DateFormatUtils.convertLongToDate(it)
            }

            if (userAuth.userInfo?.isTrial.equals("0", true)) {
                isTrailTextView.setText(R.string.no)
            } else {
                isTrailTextView.setText(R.string.yes)
            }
        }
    }

}