package com.example.pochms.data

data class AccessTokenModel (
    var access_token : String,
    var expires_in : Int,
    var token_type : String
)