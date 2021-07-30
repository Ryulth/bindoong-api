package com.bindoong.web.auth.dto

data class KakaoRegisterRequest(
    val kakaoId: String,
    val accessToken: String,
    val nickname: String,
)