package com.bindoong.web.dto

import com.bindoong.web.security.Token

data class TokenDto(
    val accessToken: String,
    val type: String,
    val refreshToken: String
) {
    companion object {
        @JvmStatic
        operator fun invoke(token: Token) = TokenDto(
            accessToken = token.accessToken,
            type = token.type,
            refreshToken = token.refreshToken
        )
    }
}
