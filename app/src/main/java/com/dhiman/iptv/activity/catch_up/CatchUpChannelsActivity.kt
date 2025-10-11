package com.dhiman.iptv.activity.catch_up

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import androidx.navigation.fragment.NavHostFragment
import com.dhiman.iptv.R
import com.dhiman.iptv.data.local.prefs.SharedPrefs
import com.dhiman.iptv.data.model.UserModel
import com.dhiman.iptv.databinding.ActivityCatchUpChannelsBinding
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
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_catch_up_channels)
        binding.viewModel = viewModel

        /** Get Data From Intent & Setup UI & Data */
     //   getDataFromIntentAndSetupUI()

        /** Observer Listeners */
       // observerListeners()

        /** Set up onFocus Change Listeners */
      //  setUpFocusChangeListeners()

       // recyclerViewSetup()


        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController

        setupSidebarNavigation()
        getIntentData()
    }

    fun getIntentData() {
        if (intent.hasExtra("type")) {
            val type = intent.getStringExtra("type") ?: ""

            // Determine the destination based on the type
            val destinationId = when (type) {
                "live" -> R.id.liveProgramList
                "movies" -> R.id.moviesActivity // This will launch an Activity
                "series" -> R.id.seriesActivity // This will launch an Activity
                "catchup" -> R.id.catchUpFragment
                // Add other types and destinations as needed
                // "search" -> R.id.searchFragment

                // It's good practice to have a default, even if it's the graph's start destination
                else -> R.id.liveProgramList
            }

            // Use the navigateIfNotCurrent function to go to the destination
            navigateIfNotCurrent(destinationId)
        }
    }

    private fun setupSidebarNavigation() {
       /* binding.ivAccount.setOnClickListener {
            navigateIfNotCurrent(R.id.multiScreenFragment)
        }*/
      /*  binding.ivSearch.setOnClickListener {
            navigateIfNotCurrent(R.id.searchFragment)
        }*/
        binding.ivLiveTv.setOnClickListener {
            navigateIfNotCurrent(R.id.liveProgramList)
        }
        binding.ivMovies.setOnClickListener {
            navigateIfNotCurrent(R.id.moviesActivity)
        }
        binding.ivSeries.setOnClickListener {
            navigateIfNotCurrent(R.id.seriesActivity)
        }
        binding.ivCatchUp.setOnClickListener {
            navigateIfNotCurrent(R.id.catchUpFragment)
        }
    }

    private fun navigateIfNotCurrent(destinationId: Int) {
        if (::navController.isInitialized && navController.currentDestination?.id != destinationId) {
            val navOptions = NavOptions.Builder()
                .setPopUpTo(R.id.catchUpFragment, true) // Key line!
                .build()
            navController.navigate(destinationId,null, navOptions)
        }
    }


    /**
     * Set up onFocus Change Listeners
     */
  /*  private fun setUpFocusChangeListeners() {
        binding.ivBack.requestFocus()
        Handler(Looper.getMainLooper()).postDelayed({
            binding.ivBack.requestFocus()
        }, 500)
    }*/

    /**
     * Observer Listeners
     */
/*    private fun observerListeners() {
        viewModel.catchUpChannelProgramsLiveData.observe(this) {
            when (it.status) {
                Status.SUCCESS -> {
                    ProgressDialogPref.hideLoader()

                    *//** Setup Channel Programs Recycler View Setup *//*
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

    *//** Get Data From Intent & Setup UI & Data *//*
    private fun getDataFromIntentAndSetupUI() {
        currentUserModel = sharedPrefs.getCurrentUser()
        if (intent.hasExtra(ConstantUtil.INTENT_STREAM_ID)) {
            streamId = intent.getStringExtra(ConstantUtil.INTENT_STREAM_ID).toString()
            val title = intent.getStringExtra(ConstantUtil.INTENT_TITLE).toString()

            binding.tvTitle.text = title

            viewModel.getAllChannelProgramsApi(currentUserModel, streamId)
        }
    }*/

}