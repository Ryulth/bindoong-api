package com.bindoong.web.auth.v1

import com.bindoong.service.user.FacebookLoginParameter
import com.bindoong.service.user.FacebookRegisterParameter
import com.bindoong.service.user.FacebookUserService
import com.bindoong.service.user.KakaoLoginParameter
import com.bindoong.service.user.KakaoRegisterParameter
import com.bindoong.service.user.KakaoUserService
import com.bindoong.service.user.UserService
import com.bindoong.web.security.Token
import com.bindoong.web.security.TokenProvider
import com.bindoong.web.security.UserSessionUtils
import io.swagger.v3.oas.annotations.Operation
import mu.KLogging
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
    private val tokenProvider: TokenProvider,
    private val userService: UserService,
    private val kakaoUserService: KakaoUserService,
    private val facebookUserService: FacebookUserService,
) {
    @Operation(
        operationId = "loginWithKakao",
        summary = "Kakao 로그인 API",
    )
    @PostMapping("/login/kakao")
    suspend fun loginWithKakao(@RequestBody loginRequest: KakaoLoginRequest): TokenResponse =
        kakaoUserService.login(loginParameter = loginRequest.toLoginParameter())
            .run { TokenResponse(tokenProvider.createToken(userId!!)) }

    @Operation(
        operationId = "registerWithKakao",
        summary = "Kakao 회원가입 API",
    )
    @PostMapping("/register/kakao")
    suspend fun registerWithKakao(@RequestBody registerRequest: KakaoRegisterRequest): TokenResponse =
        kakaoUserService.register(registerParameter = registerRequest.toRegisterParameter())
            .run { TokenResponse(tokenProvider.createToken(userId!!)) }

    @Operation(
        operationId = "loginWithFacebook",
        summary = "Facebook 로그인 API",
    )
    @PostMapping("/login/facebook")
    suspend fun loginWithFacebook(@RequestBody loginRequest: FacebookLoginRequest): TokenResponse =
        facebookUserService.login(loginParameter = loginRequest.toLoginParameter())
            .run { TokenResponse(tokenProvider.createToken(userId!!)) }

    @Operation(
        operationId = "registerWithFacebook",
        summary = "Facebook 회원가입 API",
    )
    @PostMapping("/register/facebook")
    suspend fun registerWithFacebook(@RequestBody registerRequest: FacebookRegisterRequest): TokenResponse =
        facebookUserService.register(registerParameter = registerRequest.toRegisterParameter())
            .run { TokenResponse(tokenProvider.createToken(userId!!)) }

    @Operation(
        operationId = "verifyToken",
        summary = "Validate Access Token API",
        description = "accessToken 을 보내면 status를 반환해주는 API"
    )
    @PreAuthorize("hasRole('BASIC')")
    @GetMapping("/verify")
    suspend fun verifyToken() = true

    @Operation(
        operationId = "refreshToken",
        summary = "Refresh Token API",
        description = "refreshToken 을 보내면 새로운 토큰을 보내주는 API"
    )
    @PostMapping("/refresh")
    suspend fun refreshToken(@RequestBody request: RefreshTokenRequest) =
        tokenProvider.refreshToken(request.refreshToken)

    @Operation(
        operationId = "withdrawUser",
        summary = "탈퇴 API",
        description = "accessToken 을 보내면 탈퇴시키는 API"
    )
    @PreAuthorize("hasRole('BASIC')")
    @DeleteMapping
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

    companion object : KLogging() {
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

data class RefreshTokenRequest(
    val refreshToken: String
)

data class TokenResponse(
    val accessToken: String,
    val type: String,
    val refreshToken: String
) {
    companion object {
        @JvmStatic
        operator fun invoke(token: Token) = TokenResponse(
            accessToken = token.accessToken,
            type = token.type,
            refreshToken = token.refreshToken
        )
    }
}
