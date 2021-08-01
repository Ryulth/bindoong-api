package com.bindoong.domain.user

data class UserCreateParameter(
    val nickName: String,
    val email: String?,
    val loginType: LoginType,
    val roles: Set<Role>
)

data class KakaoUserCreateParameter(
    val kakaoId: String,
    val lastAccessToken: String
)

data class FacebookUserCreateParameter(
    val facebookId: String,
    val lastAccessToken: String
)