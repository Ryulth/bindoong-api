package com.bindoong.web.security

import org.springframework.security.web.server.authentication.AuthenticationWebFilter

class TokenAuthorizationFilter(
    authenticationManager: TokenAuthenticationManager,
    tokenProvider: TokenProvider
) : AuthenticationWebFilter(authenticationManager) {

    init {
        val serverHttpBearerAuthenticationConverter =
            TokenAuthenticationConverter(tokenProvider)
        setServerAuthenticationConverter(serverHttpBearerAuthenticationConverter::apply)
    }
}
