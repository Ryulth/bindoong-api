package com.bindoong.web.security

import kotlinx.coroutines.reactor.mono
import org.springframework.security.authentication.ReactiveAuthenticationManager
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Component
import reactor.core.publisher.Mono

@Component
class TokenAuthenticationManager : ReactiveAuthenticationManager {
    override fun authenticate(authentication: Authentication): Mono<Authentication> {
        return mono {
            if (authentication is UserSession) {
                authentication.isAuthenticated = true
            }
            authentication
        }
    }
}
