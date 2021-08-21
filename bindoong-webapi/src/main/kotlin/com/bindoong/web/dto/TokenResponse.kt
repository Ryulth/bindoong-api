package com.bindoong.web.dto

import com.bindoong.web.security.Token

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
