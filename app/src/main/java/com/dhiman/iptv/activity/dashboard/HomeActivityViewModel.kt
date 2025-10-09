package com.dhiman.iptv.activity.dashboard

import android.content.Context
import android.content.Intent
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.viewModelScope
import com.dhiman.iptv.R
import com.dhiman.iptv.activity.catch_up.CatchUpChannelsActivity
import com.dhiman.iptv.activity.catch_up.CatchUpItemsListActivity
import com.dhiman.iptv.activity.category.CategoryActivity
import com.dhiman.iptv.activity.epg_category.EpgCategoryActivity
import com.dhiman.iptv.activity.live_program_list.LiveProgramListNewActivity
import com.dhiman.iptv.activity.m3u_playlist.M3UPlaylistActivity
import com.dhiman.iptv.activity.movie_program_list.MovieProgramListActivity
import com.dhiman.iptv.activity.multiScreen.MultiScreenActivity
import com.dhiman.iptv.activity.recording.RecordingActivity
import com.dhiman.iptv.activity.series_program.SeriesProgramActivity
import com.dhiman.iptv.activity.setting.SettingActivity
import com.dhiman.iptv.data.local.db.entity.CommonCategoryModel
import com.dhiman.iptv.data.local.db.entity.M3UItemModel
import com.dhiman.iptv.data.local.db.entity.M3UPlaylistModel
import com.dhiman.iptv.data.local.prefs.SharedPrefs
import com.dhiman.iptv.data.repository.RoomRepository
import com.dhiman.iptv.dialog.add_epg.AddEpgDialog
import com.dhiman.iptv.dialog.loading_dialog.LoadingDialog
import com.dhiman.iptv.util.ConstantUtil
import com.dhiman.iptv.util.DateFormatUtils
import com.dhiman.iptv.util.ParseFile
import com.dhiman.iptv.util.ProgressDialogPref
import com.dhiman.iptv.util.base.BaseViewModel
import com.dhiman.iptv.util.longToast
import dagger.hilt.android.internal.managers.FragmentComponentManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeActivityViewModel @Inject constructor(
    private val roomRepository: RoomRepository, private val sharedPrefs: SharedPrefs
) : BaseViewModel() {

    var currentUser = sharedPrefs.getCurrentUser()

    fun onClick(view: View) {
        when (view.id) {
            R.id.settings -> {
                view.context.apply {
                    startActivity(Intent(this, SettingActivity::class.java))
                }
            }

            R.id.llEpgLive -> {
                view.context.apply {
                    if (currentUser.playListType.equals(ConstantUtil.FILE, true)) {
                        val addEpgDialog = AddEpgDialog()
                        addEpgDialog.show((this as AppCompatActivity).supportFragmentManager, "")
                        addEpgDialog.callBack = {
                            startActivity(Intent(this, EpgCategoryActivity::class.java))
                        }
                    } else {
                        if (checkDataUpdateOrNot()) {
                            startActivity(Intent(this, EpgCategoryActivity::class.java))
                        } else {
                            refreshData(view.context)
                        }
                    }
                }
            }

            R.id.llCatchUp -> {
                view.context.apply {
                    if (currentUser.playListType.equals(ConstantUtil.FILE, true)) {
                        startActivity(Intent(this, M3UPlaylistActivity::class.java))
                    } else {
                        if (checkDataUpdateOrNot()) {
                            startActivity(Intent(this, CatchUpChannelsActivity::class.java))
                        } else {
                            refreshData(view.context)
                        }
                    }
                }
            }

            R.id.recordings -> {
                view.context.apply {
                    startActivity(Intent(this, RecordingActivity::class.java))
                }
            }

            R.id.movie -> {
                view.context.apply {
                    startActivity(Intent(this, CategoryActivity::class.java).putExtra("type","movies"))
                }
            }

            R.id.live -> {
                view.context.apply {
                    startActivity(Intent(this, LiveProgramListNewActivity::class.java))
                }
            }

            R.id.series -> {
                view.context.apply {
                    startActivity(Intent(this, CategoryActivity::class.java).putExtra("type","series"))
                }
            }

            R.id.refresh -> {
                if (currentUser.playListType.equals(ConstantUtil.FILE, true)) {
                    refreshLocalData(view.context, currentUser.url.toString())
                } else {
                    refreshData(view.context)
                }
            }

            R.id.llMultiScreen -> {
                view.context.apply {
                    startActivity(Intent(this, MultiScreenActivity::class.java))
                }
            }
        }
    }

    private fun checkDataUpdateOrNot(): Boolean {
        val updatedTime = sharedPrefs.getLong(ConstantUtil.CHANNEL_REFRESH_DATE)
        return DateFormatUtils.checkIsToday(updatedTime)
    }

    fun refreshData(context: Context) {
        val loadingDialog = LoadingDialog()
        loadingDialog.show((context as AppCompatActivity).supportFragmentManager, "")
        loadingDialog.isCancelable = false
        loadingDialog.callBack = {
            "Data Refresh Successfully".longToast(context)
        }
    }

    fun refreshLocalData(context: Context, url: String) {
        ProgressDialogPref.showLoader(FragmentComponentManager.findActivity(context) as FragmentActivity)

        deleteAllCommonCategoriesFromRoomDatabase()
        deleteAllM3UPlaylistFromRoomDatabase()
        deleteAllM3UPlaylistItemsFromRoomDatabase()

        try {
            val data = ParseFile.fetchAndParseFile(url)
            Log.e("Parse Data", "" + data)

            insertAllCommonCategoriesToRoomDatabase(data.commonCategoryList)
            insertM3UPlaylistToRoomDatabase(data)
            insertM3UPlaylistItemsToRoomDatabase(data.playlistItems)

            Handler(Looper.getMainLooper()).postDelayed({
                ProgressDialogPref.hideLoader()
            }, 2500)

        } catch (e: Exception) {
            ProgressDialogPref.hideLoader()
            e.message.toString().longToast(context)
        }

    }

    private fun deleteAllCommonCategoriesFromRoomDatabase() {
        viewModelScope.launch {
            roomRepository.deleteAllCommonCategories()
        }
    }

    private fun deleteAllM3UPlaylistFromRoomDatabase() {
        viewModelScope.launch {
            roomRepository.deleteAllM3UPlaylist()
        }
    }

    private fun deleteAllM3UPlaylistItemsFromRoomDatabase() {
        viewModelScope.launch {
            roomRepository.deleteAllM3UPlaylistItems()
        }
    }

    private fun insertAllCommonCategoriesToRoomDatabase(dataList: List<CommonCategoryModel>?) {
        viewModelScope.launch {
            dataList?.let { roomRepository.insertAllCommonCategories(it) }
        }
    }

    private fun insertM3UPlaylistToRoomDatabase(model: M3UPlaylistModel) {
        viewModelScope.launch {
            roomRepository.insertM3UPlaylist(model)
        }
    }

    private fun insertM3UPlaylistItemsToRoomDatabase(dataList: List<M3UItemModel>?) {
        viewModelScope.launch {
            dataList?.let { roomRepository.insertAllM3UPlaylistItems(it) }
        }
    }
}