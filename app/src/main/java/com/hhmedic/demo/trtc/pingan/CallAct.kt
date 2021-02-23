package com.hhmedic.demo.trtc.pingan

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.hhmedic.android.sdk.HHDoctor
import com.hhmedic.android.sdk.listener.HHCallExListener
import com.hhmedic.android.sdk.model.HHCallInfo
import com.hhmedic.demo.trtc.pingan.databinding.CallLayoutBinding

class CallAct : BaseAct() {

    private lateinit var mBinding:CallLayoutBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this,R.layout.call_layout)
        initUI()
    }

    private fun initUI() {
        initActionBar(mBinding.toolbar)
        setExtension()
        mBinding.callAdult.setOnClickListener {
            HHDoctor.callForAdult(this,object : HHCallExListener {
                override fun onFinish() {
                }

                override fun onCalling() {
                }

                override fun onLineUp() {
                }

                override fun onInTheCall() {
                }

                override fun onFail(code: Int) {
                }

                override fun onCancel() {
                }

                override fun onCallError(error: String?) {
                }

                override fun onLoadDoctor(callInfo: HHCallInfo?) {
                }

                override fun onDoctorAgree() {
                }

                override fun onCallSuccess() {
                }

                override fun onLineUpTimeout() {
                }

                override fun onStart(orderId: String?) {
                }

            })
        }

        mBinding.callChild.setOnClickListener {
            setExtension()
            HHDoctor.callForChild(this,object : HHCallExListener {
                override fun onFinish() {
                }

                override fun onCalling() {
                }

                override fun onLineUp() {
                }

                override fun onInTheCall() {
                }

                override fun onFail(code: Int) {
                }

                override fun onCancel() {
                }

                override fun onCallError(error: String?) {
                }

                override fun onLoadDoctor(callInfo: HHCallInfo?) {
                }

                override fun onDoctorAgree() {
                }

                override fun onCallSuccess() {
                }

                override fun onLineUpTimeout() {
                }

                override fun onStart(orderId: String?) {
                }

            })
        }

        mBinding.startCallUserToken.setOnClickListener {
            val userToken = mBinding.callUserToken.text.toString()
            if (TextUtils.isEmpty(userToken)) {
                Toast.makeText(this,"请输入需要呼叫的userToken",Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            HHDoctor.call(this,userToken,object : HHCallExListener {
                override fun onFinish() {
                }

                override fun onCalling() {
                }

                override fun onLineUp() {
                }

                override fun onInTheCall() {
                }

                override fun onFail(code: Int) {
                }

                override fun onCancel() {
                }

                override fun onCallError(error: String?) {
                }

                override fun onLoadDoctor(callInfo: HHCallInfo?) {
                }

                override fun onDoctorAgree() {
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

    private fun setExtension() {
        HHDoctor.setExtension("test extension")
    }
}