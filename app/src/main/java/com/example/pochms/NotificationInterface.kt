package com.example.pochms


import com.example.pochms.data.NotificationMessageBody
import com.example.pochms.data.NotificationMessageModel
import retrofit2.Call


import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST

interface NotificationInterface {
    @Headers("Content-Type:application/x-www-form-urlencoded; charset=UTF-8")
    @POST("v1/111255883/messages:send")
    fun createNotification(
        @Header("Authorization") authorization: String?,
        @Body notificationMessageBody: NotificationMessageBody
    ) : Call<NotificationMessageModel>
}