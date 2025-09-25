package com.dhiman.iptv.activity.catch_up

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.dhiman.iptv.R
import com.dhiman.iptv.activity.player.PlayerActivity
import com.dhiman.iptv.data.local.prefs.SharedPrefs
import com.dhiman.iptv.data.model.UserModel
import com.dhiman.iptv.data.model.catchUp.EpgListings
import com.dhiman.iptv.databinding.ActivityCatchUpChannelsBinding
import com.dhiman.iptv.util.*
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class CatchUpChannelsActivity : AppCompatActivity() {

    @Inject
    lateinit var sharedPrefs: SharedPrefs
    private lateinit var currentUserModel: UserModel
    private lateinit var binding: ActivityCatchUpChannelsBinding
    private val viewModel: CatchUpChannelVM by viewModels()
    private var streamId: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_catch_up_channels)
        binding.viewModel = viewModel

        /** Get Data From Intent & Setup UI & Data */
        getDataFromIntentAndSetupUI()

        /** Observer Listeners */
        observerListeners()

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
    private fun observerListeners() {
        viewModel.catchUpChannelProgramsLiveData.observe(this) {
            when (it.status) {
                Status.SUCCESS -> {
                    ProgressDialogPref.hideLoader()

                    /** Setup Channel Programs Recycler View Setup */
                    it.data?.epgListings?.let { list -> recyclerViewSetup(list) }
                }
                Status.LOADING -> {
                    ProgressDialogPref.showLoader(this)
                }
                Status.ERROR -> {
                    it.message?.longToast(this)
                    ProgressDialogPref.hideLoader()
                }
            }
        }
    }

    private fun recyclerViewSetup(data: List<EpgListings>) {
        val adapter = CatchUpChannelProgramAdapter(data) { pos, model ->
            val streamUrl = currentUserModel.mainUrl + "/timeshift/" +
                    currentUserModel.userName + "/" +
                    currentUserModel.password + "/" +
                    model.end?.getConvertedDateTime(
                        "yyyy-MM-dd hh:mm:ss",
                        "yyyy-MM-dd:HH-mm"
                    ) + "/"+
                    model.start?.getConvertedDateTime(
                        "yyyy-MM-dd hh:mm:ss",
                        "yyyy-MM-dd:HH-mm"
                    ) + "/"+
                    streamId + ".ts"
            val intent = Intent(this, PlayerActivity::class.java)
            intent.putExtra(ConstantUtil.INTENT_ID, streamUrl)
            startActivity(intent)
        }
        binding.rvChannels.adapter = adapter
    }

    /** Get Data From Intent & Setup UI & Data */
    private fun getDataFromIntentAndSetupUI() {
        currentUserModel = sharedPrefs.getCurrentUser()
        if (intent.hasExtra(ConstantUtil.INTENT_STREAM_ID)) {
            streamId = intent.getStringExtra(ConstantUtil.INTENT_STREAM_ID).toString()
            val title = intent.getStringExtra(ConstantUtil.INTENT_TITLE).toString()

            binding.tvTitle.text = title

            viewModel.getAllChannelProgramsApi(currentUserModel, streamId)
        }
    }

}