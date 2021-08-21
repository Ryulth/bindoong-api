package com.bindoong.web.auth.v1

import com.bindoong.service.user.FacebookLoginParameter
import com.bindoong.service.user.FacebookRegisterParameter
import com.bindoong.service.user.FacebookUserService
import com.bindoong.service.user.KakaoLoginParameter
import com.bindoong.service.user.KakaoRegisterParameter
import com.bindoong.service.user.KakaoUserService
import com.bindoong.service.user.UserService
import com.bindoong.web.dto.FacebookLoginRequest
import com.bindoong.web.dto.FacebookRegisterRequest
import com.bindoong.web.dto.KakaoLoginRequest
import com.bindoong.web.dto.KakaoRegisterRequest
import com.bindoong.web.dto.RefreshTokenRequest
import com.bindoong.web.dto.TokenDto
import com.bindoong.web.security.TokenProvider
import com.bindoong.web.security.UserSessionUtils
import io.swagger.v3.oas.annotations.Operation
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class AuthController(
    private val tokenProvider: TokenProvider,
    private val userService: UserService,
    private val kakaoUserService: KakaoUserService,
    private val facebookUserService: FacebookUserService,
) {
    @Operation(
        operationId = "loginWithKakao",
        summary = "Kakao 로그인 API",
    )
    @PostMapping("/v1/auth/login/kakao")
    suspend fun loginWithKakao(@RequestBody loginRequest: KakaoLoginRequest): TokenDto =
        kakaoUserService.login(loginParameter = loginRequest.toLoginParameter())
            .run { TokenDto(tokenProvider.createToken(userId)) }

    @Operation(
        operationId = "registerWithKakao",
        summary = "Kakao 회원가입 API",
    )
    @PostMapping("/v1/auth/register/kakao")
    suspend fun registerWithKakao(@RequestBody registerRequest: KakaoRegisterRequest): TokenDto =
        kakaoUserService.register(registerParameter = registerRequest.toRegisterParameter())
            .run { TokenDto(tokenProvider.createToken(userId)) }

    @Operation(
        operationId = "loginWithFacebook",
        summary = "Facebook 로그인 API",
    )
    @PostMapping("/v1/auth/login/facebook")
    suspend fun loginWithFacebook(@RequestBody loginRequest: FacebookLoginRequest): TokenDto =
        facebookUserService.login(loginParameter = loginRequest.toLoginParameter())
            .run { TokenDto(tokenProvider.createToken(userId)) }

    @Operation(
        operationId = "registerWithFacebook",
        summary = "Facebook 회원가입 API",
    )
    @PostMapping("/v1/auth/register/facebook")
    suspend fun registerWithFacebook(@RequestBody registerRequest: FacebookRegisterRequest): TokenDto =
        facebookUserService.register(registerParameter = registerRequest.toRegisterParameter())
            .run { TokenDto(tokenProvider.createToken(userId)) }

    @Operation(
        operationId = "verifyToken",
        summary = "Validate Access Token API",
        description = "accessToken 을 보내면 status를 반환해주는 API"
    )
    @PreAuthorize("hasRole('BASIC')")
    @GetMapping("/v1/auth/verify")
    suspend fun verifyToken() = true

    @Operation(
        operationId = "refreshToken",
        summary = "Refresh Token API",
        description = "refreshToken 을 보내면 새로운 토큰을 보내주는 API"
    )
    @PostMapping("/v1/auth/refresh")
    suspend fun refreshToken(@RequestBody request: RefreshTokenRequest) =
        tokenProvider.refreshToken(request.refreshToken)

    @Operation(
        operationId = "withdrawUser",
        summary = "탈퇴 API",
        description = "accessToken 을 보내면 탈퇴시키는 API"
    )
    @PreAuthorize("hasRole('BASIC')")
    @DeleteMapping("/v1/auth")
    suspend fun withdrawUser() {
        UserSessionUtils.getCurrentUserId().also { userId ->
            kakaoUserService.withDraw(userId)
            facebookUserService.withDraw(userId)

            // 연관 관계가 있을 수 있어서 user 는 마지막에 삭제한다.
            userService.withDraw(userId)
        }
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
}
