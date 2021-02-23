package com.hhmedic.demo.trtc.pingan

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.widget.CompoundButton
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.hhmedic.android.sdk.HHDoctor
import com.hhmedic.android.sdk.listener.HHCallListener
import com.hhmedic.android.sdk.listener.HHLoginListener
import com.hhmedic.demo.trtc.pingan.databinding.ActivityMainBinding

class MainActivity : BaseAct() {

    private lateinit var mBinding:ActivityMainBinding

    private val defaultUserToken = "8DC2FD1D49451309DF7123716BFF20843F0D04F68EA2608F6783B874E4F50EEF"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (intent.flags and Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT != 0) {
            finish()
            return
        }
        mBinding = DataBindingUtil.setContentView(this,R.layout.activity_main)
        initUI()
    }

    private fun initUI() {

        initActionBar(mBinding.toolbar)
        hideBack()

        mBinding.developSwitch.isChecked = LocalConfig.isDevelop(this)!!
        mBinding.developSwitch.setOnCheckedChangeListener { _: CompoundButton?, isChecked: Boolean ->
            LocalConfig.setDevelop(this, isChecked)
            switchReload()
        }

        mBinding.useDefaultUserToken.setOnClickListener {
            mBinding.userToken.setText(defaultUserToken)
        }

        mBinding.loginButton.setOnClickListener {
            HHDoctor.login(this, mBinding.userToken.text.toString(),object:HHLoginListener {
                override fun onSuccess() {
                    forward2Call()
                }

                override fun onError(tips: String?) {
                }

            })
        }

        mBinding.directCall.setOnClickListener {
            HHDoctor.callForAdult(this, object : HHCallListener {
                override fun onFinish() {
                }

                override fun onCalling() {
                }

                override fun onInTheCall() {
                }

                override fun onFail(code: Int) {
                }

                override fun onLineUp() {
                }

                override fun onCancel() {
                }

                override fun onCallSuccess() {
                }

                override fun onLineUpTimeout() {
                }

                override fun onStart(orderId: String?) {
                }

            })
        }
    }

    private fun forward2Call() {
        val intent = Intent(this,CallAct::class.java)
        startActivity(intent)
    }

    private fun switchReload() {
        Toast.makeText(this@MainActivity, "切换设置后需要重启打开APP才会生效", Toast.LENGTH_SHORT).show()
        Handler().postDelayed({ System.exit(0) }, 1000)
    }
}