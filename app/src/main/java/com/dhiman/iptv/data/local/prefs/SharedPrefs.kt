package com.dhiman.iptv.data.local.prefs

import android.content.SharedPreferences
import com.dhiman.iptv.data.model.UserModel
import com.dhiman.iptv.util.ConstantUtil
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.Exception
import javax.inject.Inject

class SharedPrefs @Inject constructor(private var sharePref: SharedPreferences) {
    /**
     * Function which will save the integer value to preference with given key
     */
    fun save(preferenceKey: String, integerValue: Int) {
        saveToPreference(
            preferenceKey,
            integerValue
        )
    }

    /**
     * Function which will save the double value to preference with given key
     */
    fun save(preferenceKey: String, doubleValue: Float) {
        saveToPreference(
            preferenceKey,
            doubleValue
        )
    }

    /**
     * Function which will save the long value to preference with given key
     */
    fun save(preferenceKey: String, longValue: Long) {
        saveToPreference(
            preferenceKey,
            longValue
        )
    }

    /**
     * Function which will save the boolean value to preference with given key
     */
    fun save(preferenceKey: String, booleanValue: Boolean) {
        saveToPreference(
            preferenceKey,
            booleanValue
        )
    }

    /**
     * Function which will save the string value to preference with given key
     */
    fun save(preferenceKey: String, stringValue: String?) {
        stringValue?.let {
            saveToPreference(
                preferenceKey,
                stringValue
            )
        }
    }

    /**
     * General function to save preference value
     */
    private fun saveToPreference(key: String, value: Any) {
        with(sharePref.edit()) {
            when (value) {
                is Int -> putInt(key, value)
                is Float -> putFloat(key, value)
                is Long -> putLong(key, value)
                is Boolean -> putBoolean(key, value)
                is String -> putString(key, value)
            }
            apply()
        }
    }

    /**
     * Function which will return the integer value saved in the preference corresponds to the given preference key
     */
    fun getInt(preferenceKey: String): Int {
        return sharePref.getInt(preferenceKey, 1)
    }

    /**
     * Function which will return the float value saved in the preference corresponds to the given preference key
     */
    fun getFloat(preferenceKey: String): Float {
        return sharePref.getFloat(preferenceKey, 0f)
    }

    /**
     * Function which will return the long value saved in the preference corresponds to the given preference key
     */
    fun getLong(preferenceKey: String): Long {
        return sharePref.getLong(preferenceKey, 0L)
    }

    /**
     * Function which will return the boolean value saved in the preference corresponds to the given preference key
     */
    fun getBoolean(preferenceKey: String): Boolean {
        return sharePref.getBoolean(preferenceKey, false)
    }

    /**
     * Function which will return the string value saved in the preference corresponds to the given preference key
     */
    fun getString(preferenceKey: String): String {
        return sharePref.getString(preferenceKey, "") ?: ""
    }

    fun remove(preferenceKey: String) {
        with(sharePref.edit()) {
            remove(preferenceKey)
            apply()
        }
    }

    fun clearPreference() {
        with(sharePref.edit()) {
            clear()
            apply()
        }
    }

    fun saveUserToList(audioModelArrayList: MutableList<UserModel>) {
        val json = Gson().toJson(audioModelArrayList)
        save(ConstantUtil.USER_MODEL, json)
    }

    fun loadUserList(): MutableList<UserModel> {
        val json = getString(ConstantUtil.USER_MODEL)
        val type = object : TypeToken<MutableList<UserModel>>() {}.type
        return try {
            Gson().fromJson(json, type)
        } catch (e: Exception) {
            mutableListOf()
        }
    }

    fun saveUser(model: UserModel) {
        save(ConstantUtil.LOGGED_IN, true)
        save(ConstantUtil.USER_ID, model.id)
        save(ConstantUtil.MAIN_URL, model.mainUrl)
        save(ConstantUtil.USER_NAME, model.userName)
        save(ConstantUtil.PASSWORD, model.password)
        save(ConstantUtil.PLAYLIST_NAME, model.playListName)
        save(ConstantUtil.PLAYLIST_TYPE, model.playListType)
        save(ConstantUtil.URL, model.url)

        val json = Gson().toJson(model)
        save(ConstantUtil.CURRENT_USER_MODEL, json)
    }

    fun getCurrentUser(): UserModel {
        val json = getString(ConstantUtil.CURRENT_USER_MODEL)
        val type = object : TypeToken<UserModel>() {}.type
        return try {
            Gson().fromJson(json, type)
        } catch (e: Exception) {
            UserModel()
        }
    }

}