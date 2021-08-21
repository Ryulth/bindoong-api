package com.bindoong.web.security

import org.springframework.security.core.Authentication
import org.springframework.stereotype.Service

@Service
interface TokenProvider {
    fun createToken(userId: String): Token
    fun refreshToken(refreshToken: String): Token
    fun getTokenPrefix(): String
    fun getAuthentication(accessToken: String): Authentication
}
