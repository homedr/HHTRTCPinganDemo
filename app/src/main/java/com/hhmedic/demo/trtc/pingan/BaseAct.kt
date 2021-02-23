package com.hhmedic.demo.trtc.pingan

import android.os.Build
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar

open class BaseAct : AppCompatActivity() {

    fun initActionBar(toolbar: Toolbar?) {
        if (toolbar == null) {
            return
        }
        setSupportActionBar(toolbar)
        initActionBar()
    }

    private fun initActionBar() {
        if (supportActionBar == null) {
            return
        }

        // 以下代码用于去除阴影
        if (Build.VERSION.SDK_INT >= 21) {
            supportActionBar?.elevation = 0f
        }
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    fun hideBack() {
        supportActionBar?.setDisplayHomeAsUpEnabled(false)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}