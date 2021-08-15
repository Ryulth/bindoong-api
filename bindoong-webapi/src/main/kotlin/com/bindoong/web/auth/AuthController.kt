package com.bindoong.web.auth

import com.bindoong.web.security.Token
import com.bindoong.web.security.UserSessionUtils
import io.swagger.v3.oas.annotations.Operation
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping(AuthController.BASE_PATH)
class AuthController(
    private val authApiService: AuthApiService
) {
    @Operation(
        operationId = "verifyToken",
        summary = "Validate Access Token API",
        description = "accessToken 을 보내면 status를 반환해주는 API"
    )
    @PreAuthorize("hasRole('BASIC')")
    @GetMapping("/verify")
    suspend fun verifyToken() = true

    @Operation(
        operationId = "withdrawUser",
        summary = "탈퇴 API",
        description = "accessToken 을 보내면 탈퇴시키는 API"
    )
    @PreAuthorize("hasRole('BASIC')")
    @DeleteMapping
    suspend fun withdrawUser() = authApiService.withdrawUser(UserSessionUtils.getCurrentUserId())

    @Operation(
        operationId = "loginWithKakao",
        summary = "Kakao 로그인 API",
    )
    @PostMapping("/login/kakao")
    suspend fun loginWithKakao(@RequestBody loginRequest: KakaoLoginRequest): TokenResponse =
        authApiService.loginKakaoUser(loginRequest)

    @Operation(
        operationId = "registerWithKakao",
        summary = "Kakao 회원가입 API",
    )
    @PostMapping("/register/kakao")
    suspend fun registerWithKakao(@RequestBody registerRequest: KakaoRegisterRequest): TokenResponse =
        authApiService.registerKakaoUser(registerRequest)

    @Operation(
        operationId = "loginWithFacebook",
        summary = "Facebook 로그인 API",
    )
    @PostMapping("/login/facebook")
    suspend fun loginWithFacebook(@RequestBody loginRequest: FacebookLoginRequest): TokenResponse =
        authApiService.loginFacebookUser(loginRequest)

    @Operation(
        operationId = "registerWithFacebook",
        summary = "Facebook 회원가입 API",
    )
    @PostMapping("/register/facebook")
    suspend fun registerWithFacebook(@RequestBody registerRequest: FacebookRegisterRequest): TokenResponse =
        authApiService.registerFacebookUser(registerRequest)

    companion object {
        const val BASE_PATH = "/v1/auth"
    }
}

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

data class TokenResponse(
    val accessToken: String,
    val type: String
) {
    companion object {
        @JvmStatic
        operator fun invoke(token: Token) = TokenResponse(
            accessToken = token.accessToken,
            type = token.type
        )
    }
}
