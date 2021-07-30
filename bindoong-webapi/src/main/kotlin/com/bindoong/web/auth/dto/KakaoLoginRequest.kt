package com.bindoong.web.auth.dto

data class KakaoLoginRequest(
    val kakaoId: String,
    val accessToken: String
)