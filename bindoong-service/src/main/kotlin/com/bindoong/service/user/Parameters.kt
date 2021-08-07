package com.bindoong.service.user

/**
 * 로그인 요청 DTO
 */
sealed class LoginParameter

data class KakaoLoginParameter(
    val kakaoId: String,
    val accessToken: String,
) : LoginParameter()

data class FacebookLoginParameter(
    val facebookId: String,
    val accessToken: String,
) : LoginParameter()

/**
 * 회원가입 요청 DTO
 */
sealed class RegisterParameter(
    open val nickname: String
)

data class KakaoRegisterParameter(
    val kakaoId: String,
    val accessToken: String,
    override val nickname: String
) : RegisterParameter(nickname)

data class FacebookRegisterParameter(
    val facebookId: String,
    val accessToken: String,
    override val nickname: String
) : RegisterParameter(nickname)
