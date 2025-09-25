package com.dhiman.iptv.util

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.provider.Settings
import androidx.core.app.ActivityCompat
import androidx.core.app.ActivityCompat.shouldShowRequestPermissionRationale

object PermissionUtil {
    var readExternalStorage = Manifest.permission.READ_EXTERNAL_STORAGE
    //var writeExternalStorage = Manifest.permission.WRITE_EXTERNAL_STORAGE

    fun requestPermissions(activity: Activity, permissions: Array<String>) {

        if (shouldShowRequestPermissionRationale(activity, readExternalStorage)) {
            "Permission Required for application".shortToast(activity)
            val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
            intent.data = Uri.parse("package:" + activity.packageName)
            activity.startActivity(intent)
        } else if (!hasPermissions(
                activity,
                permissions
            )
        ) {
            ActivityCompat.requestPermissions(
                activity,
                permissions,
                ConstantUtil.READ_EXTERNAL_STORAGE_PERMISSION
            )
        }
    }

    fun hasPermissions(context: Context, permissions: Array<String>): Boolean {
        for (permission in permissions) {
            if (ActivityCompat.checkSelfPermission(
                    context,
                    permission
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                return false
            }
        }
        return true
    }

}
