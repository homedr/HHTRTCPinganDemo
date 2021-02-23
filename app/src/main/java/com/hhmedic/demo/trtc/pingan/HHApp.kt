package com.hhmedic.demo.trtc.pingan

import android.app.Application
import android.content.Context
import androidx.multidex.MultiDex
import com.hhmedic.android.sdk.HHDoctor
import com.hhmedic.android.sdk.config.HHSDKOptions

class HHApp : Application() {

    private val pid = "10284"

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        MultiDex.install(applicationContext)
    }

    override fun onCreate() {
        super.onCreate()
        initSDK()
    }

    private fun initSDK() {
        val options = HHSDKOptions(pid)
        options.dev = LocalConfig.isDevelop(this)
        options.isDebug = true
        HHDoctor.init(this, options)
    }
}