package com.bindoong.web.security

data class Token(
    val accessToken: String,
    val type: String,
    val refreshToken: String,
)
