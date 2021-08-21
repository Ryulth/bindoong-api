package com.bindoong.web.dto

data class FacebookRegisterRequest(
    val facebookId: String,
    val accessToken: String,
    val nickname: String,
)
