package com.bindoong.web.auth

import com.bindoong.web.security.Token

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
