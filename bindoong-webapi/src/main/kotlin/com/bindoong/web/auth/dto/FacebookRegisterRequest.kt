package com.bindoong.web.auth.dto

data class FacebookRegisterRequest(
    val facebookId: String,
    val accessToken: String,
    val nickname: String,
)