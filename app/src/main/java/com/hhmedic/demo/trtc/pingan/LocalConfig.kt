package com.hhmedic.demo.trtc.pingan

import android.content.Context

class LocalConfig {

    companion object {
        private const val IsDevelopKey = "isDevelop"

        fun setDevelop(
            context: Context,
            isDevelop: Boolean?
        ) {
            SharedPreferenceUtils.setValue(context, IsDevelopKey, isDevelop)
        }

        fun isDevelop(context: Context): Boolean {
            return SharedPreferenceUtils.getBooleanValue(context, IsDevelopKey, true)
        }
    }
}