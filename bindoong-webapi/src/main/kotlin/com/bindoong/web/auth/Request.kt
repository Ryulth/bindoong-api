package com.bindoong.web.auth

data class FacebookLoginRequest(
    val facebookId: String,
    val accessToken: String
)

data class FacebookRegisterRequest(
    val facebookId: String,
    val accessToken: String,
    val nickname: String,
)

data class KakaoLoginRequest(
    val kakaoId: String,
    val accessToken: String
)

data class KakaoRegisterRequest(
    val kakaoId: String,
    val accessToken: String,
    val nickname: String,
)