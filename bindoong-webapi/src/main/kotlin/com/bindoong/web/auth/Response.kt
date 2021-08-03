package com.bindoong.web.auth

import com.bindoong.web.security.Token

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