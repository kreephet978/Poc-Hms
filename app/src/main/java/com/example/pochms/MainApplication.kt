package com.example.pochms

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import com.example.myhmspushnoti.data.Constants
import com.huawei.agconnect.AGConnectInstance
import com.huawei.agconnect.applinking.AGConnectAppLinking

class MainApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channelOne = NotificationChannel(
                Constants.NotificationChannelOne.ID,
                Constants.NotificationChannelOne.NAME,
                NotificationManager.IMPORTANCE_HIGH
            )
            channelOne.description = Constants.NotificationChannelOne.DESCRIPTION
            val channelTwo = NotificationChannel(
                Constants.NotificationChannelTwo.ID,
                Constants.NotificationChannelTwo.NAME,
                NotificationManager.IMPORTANCE_LOW
            )
            channelTwo.description = Constants.NotificationChannelTwo.DESCRIPTION
            getSystemService(NotificationManager::class.java).apply {
                createNotificationChannel(channelOne)
                createNotificationChannel(channelTwo)
            }
        }
    }
}