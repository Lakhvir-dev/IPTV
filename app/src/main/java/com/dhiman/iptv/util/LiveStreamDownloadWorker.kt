package com.dhiman.iptv.util

import android.content.Context
import android.os.Environment
import android.os.Handler
import android.os.Looper
import android.os.SystemClock
import android.util.Log
import android.widget.Toast
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.dhiman.iptv.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.BufferedInputStream
import java.io.File
import java.io.FileOutputStream
import java.net.URL

@Suppress("BlockingMethodInNonBlockingContext")
class LiveStreamDownloadWorker(
    private val context: Context,
    private val workerParameters: WorkerParameters
) : CoroutineWorker(context, workerParameters) {

    private var mDuration = 1
    private var timeInMilliseconds: Long = 0
    var duration = 0
    private var fileName = ""
    private var message = "Recording Completed"

    init {
        NotificationHelper.createNotification(context)
         mDuration = duration
    }


    override suspend fun doWork(): Result {
        try {
            withContext(Dispatchers.IO) {
                val url = ""
                val currentUrl = URL(url)

                val startTime = System.currentTimeMillis()
                val openConn = currentUrl.openConnection()

                val defaultFilePath =
                    File("/storage/emulated/0", context.getString(R.string.app_name))
                if (!defaultFilePath.mkdirs()) {
                    Log.e("Media File", "Directory not created$defaultFilePath")
                } else {
                    Log.e("Media File", "Directory created$defaultFilePath")
                }

                var file = File(
                    Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS),
                    fileName
                )

                if (file.exists()) {
                    fileName = System.currentTimeMillis().toString() + "_" + fileName
                    file = File(
                        Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS),
                        fileName
                    )
                }
                val `is` = openConn.getInputStream()
                val inStream = BufferedInputStream(`is`, 1024 * 5)
                val outStream = FileOutputStream(file)
                val buff = ByteArray(5 * 1024)

                val time = SystemClock.uptimeMillis()

                var line: Int?
                do {
//                    if (!PreferenceUtil.instance.getBooleanPreference(PreferenceUtil.IsRecordingInProgress, false)) {
//                        message = "Recording Stopped"
//                        break
//                    }
                    line = inStream.read(buff)
                    outStream.write(buff, 0, line)

                    timeInMilliseconds = SystemClock.uptimeMillis() - time
                    val minutes = (timeInMilliseconds / 1000 / 60) % 60
                    val sec = (timeInMilliseconds / 1000) % 60
                    var minutesString = minutes.toString()
                    if (minutesString.length == 1) {
                        minutesString = "0$minutesString"
                    }
                    var secString = sec.toString()
                    if (secString.length == 1) {
                        secString = "0$secString"
                    }
                    var durationString = mDuration.toString()
                    if (durationString.length == 1) {
                        durationString = "0$durationString"
                    }
                    NotificationHelper.updateProgress(minutesString, secString, durationString)
                } while (mDuration > minutes)

                //clean up
                outStream.flush()
                outStream.close()
                inStream.close()

                Log.i(
                    "download", "download completed in "
                            + (System.currentTimeMillis() - startTime) / 1000
                            + " sec"
                )
            }

        } catch (e: Exception) {
            //  PreferenceUtil.instance.savePreference(PreferenceUtil.IsRecordingInProgress, false)
            message = "Recording Failed"
        }

        Handler(Looper.getMainLooper()).postDelayed({
            //  PreferenceUtil.instance.savePreference(PreferenceUtil.IsRecordingInProgress, false)
            NotificationHelper.downloadComplete()
            Toast.makeText(context, message, Toast.LENGTH_LONG).show()
        }, 2000)

        return Result.success()
    }

}