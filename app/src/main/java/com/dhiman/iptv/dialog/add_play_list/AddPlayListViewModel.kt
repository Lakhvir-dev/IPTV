package com.dhiman.iptv.dialog.add_play_list

import android.app.Activity
import android.content.Context
import android.os.Environment
import android.view.View
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.dhiman.iptv.R
import com.dhiman.iptv.data.local.db.entity.UserDataModel
import com.dhiman.iptv.data.repository.MainRepository
import com.dhiman.iptv.data.repository.RoomRepository
import com.dhiman.iptv.dialog.filePickerDialog.FilePickerCustomDialogBox
import com.dhiman.iptv.dialog.filePickerDialog.Option
import com.dhiman.iptv.util.PermissionUtil
import com.dhiman.iptv.util.base.BaseViewModel
import com.dhiman.iptv.util.getCurrentDateTime
import com.dhiman.iptv.util.toSpecificString
import dagger.hilt.android.internal.managers.FragmentComponentManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.ResponseBody
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import javax.inject.Inject


@HiltViewModel
class AddPlayListViewModel @Inject constructor(
    private val roomRepository: RoomRepository,
    private val mainRepository: MainRepository
) :
    BaseViewModel() {

    val isFocusOnBack = ObservableField(true)
    val isFocusOnUploadFiles = ObservableField(false)
    val isFocusOnSelectedFiles = ObservableField(false)
    val isUploadedFileShowing = ObservableField(false)
    var filePath = MutableLiveData<String>()
    private val permissionsList =
        arrayOf(PermissionUtil.readExternalStorage)

    fun onFocusChange(view: View, value: Boolean) {
        when (view.id) {
            R.id.ivCross -> {
                isFocusOnBack.set(value)
            }
            R.id.llUploadFile -> {
                isFocusOnUploadFiles.set(value)
            }
            R.id.tilUploadFile -> {
                isFocusOnSelectedFiles.set(value)
            }
            R.id.etFileName -> {
                isFocusOnSelectedFiles.set(value)
            }
        }
    }

    fun onClick(view: View) {
        when (view.id) {
            R.id.tilUploadFile -> {
                isUploadedFileShowing.set(false)
            }
            R.id.etFileName -> {
                isUploadedFileShowing.set(false)
            }
            R.id.llUploadFile -> {
                checkPermissions(view.context)
            }
        }
    }

    private fun checkPermissions(context: Context) {
        if (!PermissionUtil.hasPermissions(context, permissionsList)) {
            PermissionUtil.requestPermissions(
                FragmentComponentManager.findActivity(context) as Activity,
                permissionsList
            )
        } else {
            filePickerDialogBox(context)
        }
    }

    /** File Picker Dialog Box */
    private fun filePickerDialogBox(context: Context) {
        val filePickerCustomDialogBox =
            FilePickerCustomDialogBox(context)
        filePickerCustomDialogBox.show()
        filePickerCustomDialogBox.setItemListener(object :
            FilePickerCustomDialogBox.DialogItemListener {
            override fun onItemClick(option: Option) {
                val url = option.path
                isUploadedFileShowing.set(true)
                filePath.value = url
            }
        })
    }

    fun addUserToRoomDatabase(model: UserDataModel) {
        viewModelScope.launch {
            roomRepository.insertUser(model)
        }
    }

    fun downloadFile(fileUrl: String, callback: (String, Boolean) -> Unit) {
        viewModelScope.launch {
            try {
                val date = getCurrentDateTime()
                val dateInString = date.toSpecificString("yy_MM_dd_HH_mm_ss")

                val destinationFile = File(
                    Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS),
                    "M3U8_File_$dateInString.m3u"
                )

                delay(500)
                val responseBody = mainRepository.downloadFile(fileUrl).body()
                val filePath = saveFile(responseBody, destinationFile.absolutePath)
                if (filePath.isEmpty()) {
                    callback.invoke("Please check your url.", false)
                } else {
                    callback.invoke(filePath, true)
                }
            } catch (e: Exception) {
                e.printStackTrace()
                callback.invoke(e.message.toString(), false)
            }

        }
    }

    /**
     * Save File in Storage
     */
    private fun saveFile(body: ResponseBody?, pathToSave: String): String {
        if (body == null)
            return ""
        var input: InputStream? = null
        try {
            input = body.byteStream()
            val fos = FileOutputStream(pathToSave)
            fos.use { output ->
                val buffer = ByteArray(4 * 1024) // or other buffer size
                var read: Int
                while (input.read(buffer).also { read = it } != -1) {
                    output.write(buffer, 0, read)
                }
                output.flush()
            }
            return pathToSave
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            input?.close()
        }
        return ""
    }

    private fun downloadDataBlocking(): String {
        val client = OkHttpClient()
        val request = Request.Builder()
            .url("http://toh-users.xyz:8080/get.php?username=apdev101&password=101apdev&type=m3u&output=mpegts")
            .build()
        val response = client.newCall(request).execute()
        return response.body.string()
    }

}