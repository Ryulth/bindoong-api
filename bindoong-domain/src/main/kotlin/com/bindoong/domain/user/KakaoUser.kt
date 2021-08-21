package com.bindoong.domain.user

data class KakaoUser(
    val kakaoId: String,
    val userId: String,
    var lastAccessToken: String
)
