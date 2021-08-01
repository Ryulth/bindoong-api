package com.bindoong.service.auth

/**
 * 로그인 요청 DTO
 */
sealed class LoginParameter

class KakaoLoginParameter(
    val kakaoId: String,
    val accessToken: String,
): LoginParameter()

class FacebookLoginParameter(
    val facebookId: String,
    val accessToken: String,
): LoginParameter()

/**
 * 회원가입 요청 DTO
 */
sealed class RegisterParameter(
    val nickname: String
)

class KakaoRegisterParameter(
    val kakaoId: String,
    val accessToken: String,
    nickname: String
): RegisterParameter(nickname)

class FacebookRegisterParameter(
    val facebookId: String,
    val accessToken: String,
    nickname: String
): RegisterParameter(nickname)