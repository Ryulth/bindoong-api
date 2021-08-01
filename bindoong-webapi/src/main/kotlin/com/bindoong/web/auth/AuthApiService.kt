package com.bindoong.web.auth

import com.bindoong.web.auth.FacebookLoginRequest
import com.bindoong.web.auth.FacebookRegisterRequest
import com.bindoong.web.auth.KakaoLoginRequest
import com.bindoong.web.auth.KakaoRegisterRequest
import com.bindoong.web.auth.TokenResponse
import com.bindoong.web.security.TokenProvider
import mu.KLogging
import org.springframework.stereotype.Service

@Service
class AuthApiService(
    private val tokenProvider: TokenProvider,
//    private val authService
//    private val kakaoAuthService: KakaoAuthService,
//    private val appleAuthService: AppleAuthService,
//    // TODO 리펙토링 가즈아
//    private val userDomainService: UserDomainService
) {
    fun registerKakaoUser(registerRequest: KakaoRegisterRequest): TokenResponse {
//        val user = kakaoAuthService.register(registerRequest)
//        return tokenProvider.createToken(user.id!!, user.nickName, user.roles)
        TODO()
    }

    fun loginKakaoUser(loginRequest: KakaoLoginRequest): TokenResponse {
//        val user = kakaoAuthService.login(loginRequest)
//        return tokenProvider.createToken(user.id!!, user.nickName, user.roles)
        TODO()
    }

    fun registerFacebookUser(registerRequest: FacebookRegisterRequest): TokenResponse {
//        val user = facebookAuthService.register(registerRequest)
//        return tokenProvider.createToken(user.id!!, user.nickName, user.roles)
        TODO()
    }

    fun loginFacebookUser(loginRequest: FacebookLoginRequest): TokenResponse {
//        val user = facebookAuthService.login(loginRequest)
//        return tokenProvider.createToken(user.id!!, user.nickName, user.roles)
        TODO()
    }

    fun refreshToken(refreshToken: String): TokenResponse {
//        val token: String = tokenProvider.removePrefix(refreshToken)
//        return tokenProvider.refreshToken(token)
        TODO()
    }

    fun withdrawUser(userId: Long) {
        //authDomainService.withdraw
//        val user = userService.get(userId)
//        when (user.loginType) {
//            LoginType.KAKAO -> kakaoAuthService.withDraw(userId)
//            LoginType.APPLE -> appleAuthService.withDraw(userId)
//            LoginType.EMAIL -> logger.error { "Email User not supported" }
//        }
    }

    companion object: KLogging()
}