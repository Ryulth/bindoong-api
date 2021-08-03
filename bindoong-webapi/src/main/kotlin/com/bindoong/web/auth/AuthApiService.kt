package com.bindoong.web.auth

import com.bindoong.service.user.FacebookLoginParameter
import com.bindoong.service.user.FacebookRegisterParameter
import com.bindoong.service.user.FacebookUserService
import com.bindoong.service.user.KakaoLoginParameter
import com.bindoong.service.user.KakaoRegisterParameter
import com.bindoong.service.user.KakaoUserService
import com.bindoong.service.user.UserService
import com.bindoong.web.security.TokenProvider
import mu.KLogging
import org.springframework.stereotype.Service

@Service
class AuthApiService(
    private val tokenProvider: TokenProvider,
    private val userService: UserService,
    private val kakaoUserService: KakaoUserService,
    private val facebookUserService: FacebookUserService,
) {
    suspend fun registerKakaoUser(registerRequest: KakaoRegisterRequest): TokenResponse {
        val user = kakaoUserService.register(registerParameter = registerRequest.toRegisterParameter())
        return TokenResponse(tokenProvider.createToken(user.id!!))
    }

    suspend fun loginKakaoUser(loginRequest: KakaoLoginRequest): TokenResponse {
        val user = kakaoUserService.login(loginRequest.toLoginParameter())
        return TokenResponse(tokenProvider.createToken(user.id!!))
    }

    suspend fun registerFacebookUser(registerRequest: FacebookRegisterRequest): TokenResponse {
        val user = facebookUserService.register(registerParameter = registerRequest.toRegisterParameter())
        return TokenResponse(tokenProvider.createToken(user.id!!))
    }

    suspend fun loginFacebookUser(loginRequest: FacebookLoginRequest): TokenResponse {
        val user = facebookUserService.login(loginRequest.toLoginParameter())
        return TokenResponse(tokenProvider.createToken(user.id!!))
    }

//    fun refreshToken(refreshToken: String): TokenResponse {
//        val token: String = tokenProvider.removePrefix(refreshToken)
//        return tokenProvider.refreshToken(token)
//    }

    suspend fun withdrawUser(userId: Long) {
        kakaoUserService.withDraw(userId)
        facebookUserService.withDraw(userId)

        // 연관 관계가 있을 수 있어서 user 는 마지막에 삭제한다.
        userService.withDraw(userId)
    }

    private fun KakaoRegisterRequest.toRegisterParameter() = KakaoRegisterParameter(
        kakaoId = kakaoId,
        accessToken = accessToken,
        nickname = nickname
    )

    private fun KakaoLoginRequest.toLoginParameter() = KakaoLoginParameter(
        kakaoId = kakaoId,
        accessToken = accessToken
    )

    private fun FacebookRegisterRequest.toRegisterParameter() = FacebookRegisterParameter(
        facebookId = facebookId,
        accessToken = accessToken,
        nickname = nickname
    )

    private fun FacebookLoginRequest.toLoginParameter() = FacebookLoginParameter(
        facebookId = facebookId,
        accessToken = accessToken
    )

    companion object : KLogging()
}