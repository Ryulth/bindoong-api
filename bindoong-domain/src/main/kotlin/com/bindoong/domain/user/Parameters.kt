package com.bindoong.domain.user

data class UserCreateParameter(
    val nickName: String,
    val loginType: LoginType,
    val roles: Set<Role>
)

data class KakaoUserCreateParameter(
    val kakaoId: String,
    val userId: Long,
    val lastAccessToken: String
)

data class FacebookUserCreateParameter(
    val facebookId: String,
    val userId: Long,
    val lastAccessToken: String
)
