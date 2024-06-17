package com.example.pochms.data

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class AccessTokenRequest(
    @SerializedName("grant_type")
    val grantType: String = "",
    @SerializedName("client_secret")
    val clientSecret: String = "",
    @SerializedName("client_id")
    val clientId: String = ""
):Serializable