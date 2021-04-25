package com.hhmedic.demo.trtc.pingan

import android.content.Context
import com.hhmedic.android.sdk.HHDoctor
import com.hhmedic.android.sdk.base.utils.Logger
import com.hhmedic.android.sdk.base.utils.Maps
import com.hhmedic.android.sdk.listener.HHCallExListener
import com.hhmedic.android.sdk.logger.SystemNetLog
import com.hhmedic.android.sdk.model.HHCallInfo
import com.hhmedic.android.sdk.tim.HHRtcAccount
import com.tencent.imsdk.v2.*
import java.io.UnsupportedEncodingException
import java.nio.charset.Charset

object Tim {

    private const val APP_ID = 1400073238
    private const val ACTION = "action"
    fun init(context: Context?) {
        try {

//            if (SessionWrapper.isMainProcess(context)){
            V2TIMManager.getInstance()
                .initSDK(context, APP_ID, V2TIMSDKConfig(), object : V2TIMSDKListener() {
                    override fun onConnecting() {
                        super.onConnecting()
                    }

                    override fun onConnectSuccess() {
                        super.onConnectSuccess()
                    }

                    override fun onConnectFailed(
                        code: Int,
                        error: String
                    ) {
                        SystemNetLog.send(
                            context,
                            Maps.of(
                                ACTION,
                                "onDisconnected",
                                "code",
                                code.toLong()
                            )
                        )
                    }

                    override fun onKickedOffline() {
                        SystemNetLog.send(
                            context,
                            Maps.of(ACTION, "onForceOffline")
                        )
                    }

                    override fun onUserSigExpired() {
                        HHRtcAccount.doAutoLoginWhenSigExpired(context)
                        SystemNetLog.send(
                            context,
                            Maps.of(
                                ACTION,
                                "onUserSigExpired"
                            )
                        )
                    }

                    override fun onSelfInfoUpdated(info: V2TIMUserFullInfo) {
                        super.onSelfInfoUpdated(info)
                    }
                })
            V2TIMManager.getInstance().addSimpleMsgListener(object : V2TIMSimpleMsgListener() {
                override fun onRecvC2CTextMessage(
                    msgID: String,
                    sender: V2TIMUserInfo,
                    text: String
                ) {
                    super.onRecvC2CTextMessage(msgID, sender, text)
                }

                override fun onRecvC2CCustomMessage(
                    msgID: String,
                    sender: V2TIMUserInfo,
                    customData: ByteArray
                ) {

//                        NewMessage.getInstance(context).onNewMessage(customData);
                    try {
                        val json = String(customData, Charset.forName("UTF-8"))
                        HHDoctor.remoteMessage(context, json, object : HHCallExListener {
                            override fun onLoadDoctor(callInfo: HHCallInfo) {}
                            override fun onDoctorAgree() {}
                            override fun onCallError(error: String) {}
                            override fun onStart(orderId: String) {}
                            override fun onCalling() {}
                            override fun onInTheCall() {}
                            override fun onFinish(time: Long) {
                                Logger.e("get Video onFinish and time=$time")
                            }

                            override fun onCallSuccess() {}
                            override fun onFail(code: Int) {}
                            override fun onCancel() {}
                            override fun onLineUpTimeout() {}
                            override fun onLineUp() {}
                        })
                    } catch (e: UnsupportedEncodingException) {
                        e.printStackTrace()
                    }
                }

                override fun onRecvGroupTextMessage(
                    msgID: String,
                    groupID: String,
                    sender: V2TIMGroupMemberInfo,
                    text: String
                ) {
                    super.onRecvGroupTextMessage(msgID, groupID, sender, text)
                }

                override fun onRecvGroupCustomMessage(
                    msgID: String,
                    groupID: String,
                    sender: V2TIMGroupMemberInfo,
                    customData: ByteArray
                ) {
                    super.onRecvGroupCustomMessage(msgID, groupID, sender, customData)
                }
            })
            doLogin(context)

//            }
        } catch (e: Exception) {
        }
    }

    fun doLogin(context: Context?) {

        var token =
            "eJxNkF1PgzAUhv8L18b0Y5TGxIuOzW0CJrgxzW6aCmesG0JHO3Ux-neBYOK5fJ68Oe85394mXt8qY3QhlZO0Lbw7D3k3A4Yvo1uQau*g7TDFGE1QN6P*gNbqpu4MQdjHhKJ-UhdQO73XQxIjxAljFI-S6rKjyTwLV*ns*DDb2oma1*uounL-7WVLjmEGU1FClapYiGb5STCPnhZiVW6S6pmRNDskpjmfXmEZ*jsbccvM9BGC2B78c74IL*WuEfd-y4qTHA7si-T9A0ooH6XT79Bzhn0eMITpyFWeN5faSXc1MHzk5xfsuFc7"

        var userId = "100826631"

        context?.apply {
            if (!LocalConfig.isDevelop(this)) {
                token = "eJxNkNFOg0AQRf*FV43dZQquJr6oCG3ApLQN9mmzgQVnq7DdLrVo-HeB0MR5PCc3c2d*nE28vhFaY8GF5WAK594hzvWI5VmjkVyUVpoeA6VkTvqZ9EmaIzZ1b1xCPeoC*SexkLXFEsckBQ*A*jC5I1Y9TILt02L1nEU7kQfp*5XZLk1IO-yaiapRVqls3s3Ux8s5TE5Zww5staheD3FWbVpXp62Lb*UjUd85C3epNIGI431E2DKJ6oBilDxclhV7Pt439Bjq34ILbJIWP*XAferd*R6QCxd53rS15bbTcnzI7x9KxVgF"
                userId = "13533163"
            }
            V2TIMManager.getInstance().login(userId, token, object : V2TIMCallback {
                override fun onError(code: Int, error: String) {
                    SystemNetLog.send(
                        context,
                        Maps.of(
                            "action",
                            "IM_LOGIN_FAIL",
                            "message",
                            "登录失败 code=$code"
                        )
                    )

                    //用户如果在离线状态下被踢，下次登录将会失败，可以给用户一个非常强的提醒（登录错误码 ERR_IMSDK_KICKED_BY_OTHERS：6208），开发者也可以选择忽略这次错误，再次登录即可。
                    if (code == 6208) {
                        //被踢出后需要
                        doLogin(context)
                        return
                    }
                    if (code == 7501) {

                        //连续登录 引起的错误
                    }


//                callback.onResult(false,"登录失败 code="+code);
                }

                override fun onSuccess() {

//                callback.onResult(true,"");
                    SystemNetLog.send(
                        context,
                        Maps.of("action", "IM_LOGIN_SUCCESS")
                    )
                }
            })
        }


    }
}