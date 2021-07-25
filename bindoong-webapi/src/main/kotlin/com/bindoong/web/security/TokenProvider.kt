package com.bindoong.web.security

import org.springframework.security.core.Authentication
import org.springframework.stereotype.Service

@Service
interface TokenProvider {
    fun createToken(userId: Long): String
    fun getTokenPrefix(): String
    fun getAuthentication(token: String): Authentication
}