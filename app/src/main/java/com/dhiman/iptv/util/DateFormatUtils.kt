package com.dhiman.iptv.util

import android.text.format.DateFormat
import java.text.SimpleDateFormat
import java.util.*

object DateFormatUtils {
    fun todayStartTime(): Long {
        val date = Date()
        val calendar = Calendar.getInstance(TimeZone.getDefault(), Locale.ENGLISH)
        calendar.time = date
        calendar.set(Calendar.HOUR_OF_DAY, 0)
        calendar.set(Calendar.MINUTE, 0)
        calendar.set(Calendar.SECOND, 0)
        calendar.set(Calendar.MILLISECOND, 0)
        return calendar.timeInMillis
    }

    fun convertLongToDate(timeString: String): String {
        if (timeString.isEmpty()) return "Unlimited"
        val cal = Calendar.getInstance(Locale.ENGLISH)
        cal.timeInMillis = (timeString.toLong() * 1000L)
        return DateFormat.format("MMM dd, yyyy", cal).toString()
    }

    fun convertLongToTime(timeString: String): String {
        val originalFormat = SimpleDateFormat("yyyyMMddHHmmss", Locale.getDefault())
        val targetFormat = SimpleDateFormat("HH:mm a", Locale.getDefault())
        val convertedDateTime = try {
            val splitString = timeString.split(" ").toTypedArray()
            val date = originalFormat.parse(splitString[0])
            date?.let { targetFormat.format(it) }.toString()
        } catch (e: Exception) {
            e.printStackTrace()
            ""
        }
        return convertedDateTime
    }


    fun checkIsToday(dateMillis: Long): Boolean {
        return todayStartTime() == dateMillis
    }
}