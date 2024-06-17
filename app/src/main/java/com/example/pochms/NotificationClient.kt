package com.example.pochms

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class NotificationClient {
    companion object {
        private const val TIMEOUT: Long = 500000

        fun getClient() : Retrofit {
            val loggingInterceptor = HttpLoggingInterceptor()
            loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

            val client = OkHttpClient.Builder()
                .connectTimeout(TIMEOUT,TimeUnit.SECONDS)
                .writeTimeout(TIMEOUT,TimeUnit.SECONDS)
                .readTimeout(TIMEOUT,TimeUnit.SECONDS)
                .addInterceptor(loggingInterceptor).build()
            return Retrofit.Builder()
                .baseUrl("https://push-api.cloud.huawei.com/").client(client)
                .addConverterFactory(GsonConverterFactory.create()).build()
        }
    }
}