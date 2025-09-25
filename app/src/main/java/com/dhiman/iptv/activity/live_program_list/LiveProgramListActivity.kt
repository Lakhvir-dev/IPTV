package com.dhiman.iptv.activity.live_program_list

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.dhiman.iptv.R
import com.dhiman.iptv.data.local.db.entity.LiveStreamCategory
import com.dhiman.iptv.data.local.prefs.SharedPrefs
import com.dhiman.iptv.data.model.UserModel
import com.dhiman.iptv.databinding.ActivityLiveProgramListBinding
import com.dhiman.iptv.util.ConstantUtil
import com.bumptech.glide.Glide
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class LiveProgramListActivity : AppCompatActivity() {

    @Inject
    lateinit var sharedPrefs: SharedPrefs
    private val viewModel: LiveProgramListViewModel by viewModels()
    private lateinit var binding: ActivityLiveProgramListBinding
    private lateinit var dataModel: LiveStreamCategory
    private lateinit var currentUserModel: UserModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_live_program_list)
        binding.viewModel = viewModel

        /** Get Intent Data & Fetch Live Info */
        getDataIntentAndGetMovieInfo()

        /** Observer Listeners */
        setupObserver()
    }


    /**
     * Get Intent Data & Fetch Live Info
     */
    private fun getDataIntentAndGetMovieInfo() {
        currentUserModel = sharedPrefs.getCurrentUser()

        val intent = intent
        if (intent.hasExtra(ConstantUtil.INTENT_ID)) {
            val streamId = intent.getStringExtra(ConstantUtil.INTENT_ID).toString()
            val liveProgramName = intent.getStringExtra(ConstantUtil.LIVE_PROGRAM_NAME).toString()
            val liveProgramImage = intent.getStringExtra(ConstantUtil.LIVE_PROGRAM_IMAGE).toString()
            val dataModelString = intent.getStringExtra(ConstantUtil.DATA_MODEL).toString()
            dataModel = Gson().fromJson(dataModelString, LiveStreamCategory::class.java)
            viewModel.isFavMovie.postValue(dataModel.isFav)
            viewModel.dataModel.postValue(dataModel)

            val liveFullUrl =
                currentUserModel.mainUrl + "/" +
                        currentUserModel.userName + "/" +
                        currentUserModel.password + "/" +
                        streamId
            viewModel.liveFullLink.postValue(liveFullUrl)

            binding.apply {
                liveProgramNameTextView.text = liveProgramName

                try {
                    Glide.with(this@LiveProgramListActivity)
                        .load(liveProgramImage)
                        .placeholder(R.drawable.horizontal_image_place_holder)
                        .error(R.drawable.horizontal_image_place_holder)
                        .into(liveImageView)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
    }

    /**
     * Observer Listeners
     */
    private fun setupObserver() {
        viewModel.isFavMovie.observe(this) {
            if (it) {
                binding.btnAddToFavourite.setText(R.string.remove_from_favourite)
            } else {
                binding.btnAddToFavourite.setText(R.string.add_to_favourite)
            }
        }
    }

    override fun onBackPressed() {
        val returnIntent = Intent()
        returnIntent.putExtra("is_fav", viewModel.isFavMovie.value)
        returnIntent.putExtra("pos", intent.getIntExtra(ConstantUtil.POSITION, 0))
        setResult(RESULT_OK, returnIntent)
        finish()
        super.onBackPressed()
    }

}