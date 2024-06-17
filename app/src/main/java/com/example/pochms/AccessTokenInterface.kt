package com.example.pochms

import com.example.pochms.data.AccessTokenModel
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface AccessTokenInterface {

    @FormUrlEncoded
    @POST("oauth2/v3/token")
    fun createAccessToken(
        @Field("grant_type") grant_type : String,
        @Field("client_secret") client_secret : String,
        @Field("client_id") client_id : String) : Call<AccessTokenModel>

}