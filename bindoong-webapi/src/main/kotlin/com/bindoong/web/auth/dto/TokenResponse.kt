package com.bindoong.web.auth.dto

data class TokenResponse(
    val accessToken: String,
    val type: String
)