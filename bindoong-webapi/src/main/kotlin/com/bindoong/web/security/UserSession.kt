package com.bindoong.web.security

import org.springframework.security.authentication.AbstractAuthenticationToken
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority

data class UserSession(
    val userId: Long,
    val token: String
) : AbstractAuthenticationToken(setOf(SimpleGrantedAuthority("ROLE_USER"))) {

    override fun getAuthorities(): Collection<GrantedAuthority> {
        return super.getAuthorities()
    }

    override fun getCredentials(): Any? {
        return null
    }

    override fun getPrincipal(): Any? {
        return null
    }
}
