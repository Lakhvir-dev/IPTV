package com.dhiman.iptv.dialog.add_epg

import android.view.View
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.dhiman.iptv.R
import com.dhiman.iptv.data.local.db.entity.EpgModel
import com.dhiman.iptv.data.local.db.entity.LiveCategoryModel
import com.dhiman.iptv.data.local.db.entity.LiveStreamCategory
import com.dhiman.iptv.data.model.UserAuth
import com.dhiman.iptv.data.model.UserModel
import com.dhiman.iptv.data.repository.MainRepository
import com.dhiman.iptv.data.repository.RoomRepository
import com.dhiman.iptv.util.ConstantUtil
import com.dhiman.iptv.util.NetworkHelper
import com.dhiman.iptv.util.Resource
import com.dhiman.iptv.util.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import fr.arnaudguyon.xmltojsonlib.XmlToJson
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddEpgViewModel @Inject constructor(
    private val mainRepository: MainRepository,
    private val networkHelper: NetworkHelper,
    private val roomRepository: RoomRepository
) : BaseViewModel() {

    val userAuth = MutableLiveData<Resource<UserAuth>>()
    val epgData = MutableLiveData<Resource<List<EpgModel>>>()
    val liveCategoriesData = MutableLiveData<Resource<List<LiveCategoryModel>>>()
    val liveStreamCategoriesData = MutableLiveData<Resource<List<LiveStreamCategory>>>()

    val isFocusOnBack = ObservableField(true)

    fun onFocusChange(view: View, value: Boolean) {
        when (view.id) {
            R.id.ivCross -> {
                isFocusOnBack.set(value)
            }
        }
    }

    fun userAuth(baseUrl: String, username: String, password: String) {
        viewModelScope.launch {
            userAuth.postValue(Resource.loading(null))
            if (networkHelper.isNetworkConnected()) {
                mainRepository.userAuth(baseUrl, username, password).let {
                    if (it.isSuccessful && it.body()?.userInfo?.auth == 1 && it.body()!!.userInfo?.status.equals(
                            "Active",
                            true
                        )) {
                        userAuth.postValue(Resource.success(it.body()))
                    } else {
                        userAuth.postValue(Resource.error("URL is not valid or Unauthorised User", null))
                    }
                }
            } else {
                userAuth.postValue(Resource.error("No internet connection", null))
            }
        }
    }

    fun getAllEpg(currentUserModel: UserModel) {
        viewModelScope.launch {
            epgData.postValue(Resource.loading(null))
            if (networkHelper.isNetworkConnected()) {
                try {
                    coroutineScope {
                        val getEPGCall = async {
                            mainRepository.getEPG(
                                currentUserModel
                            )
                        }
                        val getLiveCategoriesCall = async {
                            mainRepository.getLiveCategories(
                                currentUserModel,
                                ConstantUtil.LIVE_ACTION
                            )
                        }
                        val getLiveStreamCategoriesCall = async {
                            mainRepository.getLiveStreamCategories(
                                currentUserModel,
                                ConstantUtil.LIVE_STREAM_ACTION
                            )
                        }

                        val getEPGResponse =
                            getEPGCall.await()
                        val getLiveCategoriesResponse = getLiveCategoriesCall.await()
                        val getLiveStreamCategoriesResponse = getLiveStreamCategoriesCall.await()

                        liveCategoriesData.postValue(Resource.success(getLiveCategoriesResponse.body()))
                        liveStreamCategoriesData.postValue(
                            Resource.success(
                                getLiveStreamCategoriesResponse.body()
                            )
                        )
                        getEPGResponse.body()?.let { response ->
                            val xmlToJson = XmlToJson.Builder(response).build()
                            val jsonObject = xmlToJson.toJson()
                            jsonObject?.let {
                                val dataArrayList = ArrayList<EpgModel>()

                                val tvJsonObject = it.getJSONObject("tv")
                                val programJsonArray = tvJsonObject.getJSONArray("programme")
                                for (x in 0 until programJsonArray.length()) {
                                    val dataObject = programJsonArray.getJSONObject(x)
                                    val epgModel = EpgModel()
                                    epgModel.channel = dataObject.getString("channel")
                                    epgModel.desc = dataObject.getString("desc")
                                    epgModel.start = dataObject.getString("start")
                                    epgModel.stop = dataObject.getString("stop")
                                    epgModel.title = dataObject.getString("title")
                                    dataArrayList.add(epgModel)
                                }
                                epgData.postValue(Resource.success(dataArrayList))
                            }
                        }
                    }
                } catch (e: Exception) {
                    epgData.postValue(Resource.error(e.localizedMessage, null))
                }
            } else {
                epgData.postValue(Resource.error("No internet connection", null))
            }
        }
    }

    fun deleteAllEPGFromRoomDatabase() {
        viewModelScope.launch {
            roomRepository.deleteAllLiveCategories()
            roomRepository.deleteAllLiveStreamCategories()
            roomRepository.deleteAllEpg()
        }
    }

    fun insertAllLiveCategoriesToRoomDatabase(dataList: List<LiveCategoryModel>?) {
        viewModelScope.launch {
            dataList?.let { roomRepository.insertAllLiveCategories(it) }
        }
    }

    fun insertAllLiveStreamCategoriesToRoomDatabase(dataList: List<LiveStreamCategory>?) {
        viewModelScope.launch {
            dataList?.let { roomRepository.insertAllLiveStreamCategories(it) }
        }
    }

    fun insertAllEpgToRoomDatabase(dataList: List<EpgModel>?) {
        viewModelScope.launch {
            dataList?.let { roomRepository.insertAllEpg(it) }
        }
    }

}