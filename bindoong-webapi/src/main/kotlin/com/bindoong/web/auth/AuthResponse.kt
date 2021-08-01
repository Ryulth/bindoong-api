package com.bindoong.web.auth

data class TokenResponse(
    val accessToken: String,
    val type: String
)