package com.dhiman.iptv.activity.live_program_list

import android.content.Intent
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.dhiman.iptv.R
import com.dhiman.iptv.activity.player.PlayerActivity
import com.dhiman.iptv.data.local.db.entity.LiveStreamCategory
import com.dhiman.iptv.data.repository.RoomRepository
import com.dhiman.iptv.util.ConstantUtil
import com.dhiman.iptv.util.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LiveProgramListViewModel @Inject constructor(private val roomRepository: RoomRepository) :
    BaseViewModel() {

    val isFocusOnBack = ObservableField(false)
    val isFocusWatchNow = ObservableField(false)
    val liveFullLink = MutableLiveData<String>()
    val isFavMovie = MutableLiveData<Boolean>()
    val dataModel = MutableLiveData<LiveStreamCategory>()

    fun onFocusChange(view: View, value: Boolean) {
        when (view.id) {
            R.id.ivBack -> {
                isFocusOnBack.set(value)
            }

            R.id.llWatchNow -> {
                isFocusWatchNow.set(value)
            }
        }
    }

    fun onClick(view: View) {
        when (view.id) {
            R.id.ivBack -> {
                (view.context as AppCompatActivity).onBackPressed()
            }

            R.id.llWatchNow -> {
                view.context.apply {
                    if (liveFullLink.value?.isNotEmpty()!!) {
                        val intent = Intent(this, PlayerActivity::class.java)
                        intent.putExtra(ConstantUtil.INTENT_ID, liveFullLink.value.toString())
                        startActivity(intent)
                    }
                }
            }

            R.id.btnAddToFavourite -> {
                view.context.apply {
                    dataModel.value?.let {
                        val isFav = it.isFav
                        it.isFav = !isFav
                        isFavMovie.postValue(!isFav)
                        updateLiveStreamCategory(it)
                    }
                }
            }
        }
    }

    /**
     * Update Live Stream Category
     */
    private fun updateLiveStreamCategory(model: LiveStreamCategory) {
        viewModelScope.launch {
            roomRepository.updateLiveStreamCategory(model)
        }
    }
}