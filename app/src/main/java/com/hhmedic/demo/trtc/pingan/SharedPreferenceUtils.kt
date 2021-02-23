package com.hhmedic.demo.trtc.pingan

import android.content.Context
import android.content.SharedPreferences

class SharedPreferenceUtils {
    companion object {
        private const val PREFERENCE_NAME = "HH_SDK_DEMO"

        fun setValue(
            context: Context,
            key: String?,
            value: String?
        ) {
            val editor = getEditor(context)
            editor.putString(key, value)
            editor.apply()
        }

        fun setValue(
            context: Context,
            key: String?,
            value: Boolean?
        ) {
            val editor = getEditor(context)
            editor.putBoolean(key, value!!)
            editor.apply()
        }

        fun getStringValue(context: Context, key: String): String? {
            return getSharePreference(context).getString(key, "")
        }

        fun getBooleanValue(
            context: Context,
            key: String,
            defaultValue: Boolean
        ): Boolean {
            return getSharePreference(context).getBoolean(key, defaultValue)
        }

        private fun getSharePreference(context: Context): SharedPreferences {
            return context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE)
        }

        private fun getEditor(context: Context): SharedPreferences.Editor {
            val preferences = getSharePreference(context)
            return preferences.edit()
        }
    }
}