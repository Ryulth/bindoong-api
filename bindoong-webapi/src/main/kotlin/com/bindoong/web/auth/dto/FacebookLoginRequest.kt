package com.bindoong.web.auth.dto

data class FacebookLoginRequest(
    val facebookId: String,
    val accessToken: String
)