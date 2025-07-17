package com.nativedevps.myapplication.service

import com.nativedevps.arch.R
import com.nativedevps.myapplication.ui.home.HomeActivity
import com.nativedevps.support.base.fcm.NativeDevelopersFCMService
import com.nativedevps.support.base.fcm.model.NotificationModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FcmService() : NativeDevelopersFCMService() {
    override fun functionalEvent(notificationModel: NotificationModel) {

    }

    override fun getAppIcon(): Int {
        return R.mipmap.ic_launcher
    }

    override fun launchActivityName(): Class<out Any> {
        return HomeActivity::class.java
    }
}