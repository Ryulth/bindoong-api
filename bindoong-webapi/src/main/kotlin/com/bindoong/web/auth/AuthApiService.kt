package com.bindoong.web.auth

import com.bindoong.service.user.FacebookAccountService
import com.bindoong.service.user.FacebookLoginParameter
import com.bindoong.service.user.FacebookRegisterParameter
import com.bindoong.service.user.KakaoAccountService
import com.bindoong.service.user.KakaoLoginParameter
import com.bindoong.service.user.KakaoRegisterParameter
import com.bindoong.service.user.UserService
import com.bindoong.web.security.TokenProvider
import mu.KLogging
import org.springframework.stereotype.Service

@Service
class AuthApiService(
    private val tokenProvider: TokenProvider,
    private val userService: UserService,
    private val kakaoAccountService: KakaoAccountService,
    private val facebookAccountService: FacebookAccountService,
) {
    suspend fun registerKakaoUser(registerRequest: KakaoRegisterRequest): TokenResponse {
        val user = kakaoAccountService.register(registerParameter = registerRequest.toRegisterParameter())
        return TokenResponse(tokenProvider.createToken(user.userId!!))
    }

    suspend fun loginKakaoUser(loginRequest: KakaoLoginRequest): TokenResponse {
        val user = kakaoAccountService.login(loginParameter = loginRequest.toLoginParameter())
        return TokenResponse(tokenProvider.createToken(user.userId!!))
    }

    suspend fun registerFacebookUser(registerRequest: FacebookRegisterRequest): TokenResponse {
        val user = facebookAccountService.register(registerParameter = registerRequest.toRegisterParameter())
        return TokenResponse(tokenProvider.createToken(user.userId!!))
    }

    suspend fun loginFacebookUser(loginRequest: FacebookLoginRequest): TokenResponse {
        val user = facebookAccountService.login(loginParameter = loginRequest.toLoginParameter())
        return TokenResponse(tokenProvider.createToken(user.userId!!))
    }

//    fun refreshToken(refreshToken: String): TokenResponse {
//        val token: String = tokenProvider.removePrefix(refreshToken)
//        return tokenProvider.refreshToken(token)
//    }

    suspend fun withdrawUser(userId: Long) {
        kakaoAccountService.withDraw(userId)
        facebookAccountService.withDraw(userId)

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
