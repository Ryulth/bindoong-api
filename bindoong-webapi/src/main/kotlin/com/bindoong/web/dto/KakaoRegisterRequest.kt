package com.bindoong.web.dto
data class KakaoRegisterRequest(
    val kakaoId: String,
    val accessToken: String,
    val nickname: String,
)
