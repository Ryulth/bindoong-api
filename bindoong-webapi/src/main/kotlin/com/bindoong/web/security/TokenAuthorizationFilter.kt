package com.bindoong.web.security

import kotlinx.coroutines.reactor.mono
import mu.KLogging
import org.springframework.http.HttpHeaders
import org.springframework.security.core.Authentication
import org.springframework.security.web.server.authentication.AuthenticationWebFilter
import org.springframework.web.server.ServerWebExchange
import org.springframework.web.util.pattern.PathPatternParser
import reactor.core.publisher.Mono

class TokenAuthorizationFilter(
    authenticationManager: TokenAuthenticationManager,
    private val tokenProvider: TokenProvider
) : AuthenticationWebFilter(authenticationManager) {

    init {
        setServerAuthenticationConverter(this::convert)
    }

    private fun convert(exchange: ServerWebExchange): Mono<Authentication> =
        mono {
            var authentication: Authentication? = null
            if (ALLOWED_PATHS.firstOrNull { it.matches(exchange.request.path.pathWithinApplication()) } != null) {
                return@mono authentication
            }
            val headers = exchange.request.headers[HttpHeaders.AUTHORIZATION]
            headers?.forEach { token ->
                val tokenPrefix = tokenProvider.getTokenPrefix()
                if (token.startsWith(tokenPrefix)) {
                    val accessToken = token.substring(tokenPrefix.length + 1).trim()
                    authentication = tokenProvider.getAuthentication(accessToken)
                        .also { logger.debug { "authentication $authentication" } }
                }
            }
            authentication
        }

    companion object : KLogging() {
        private val ALLOWED_PATHS = listOf(
            PathPatternParser().parse("/v1/auth/login/**"),
            PathPatternParser().parse("/v1/auth/register/**"),
            PathPatternParser().parse("/v1/auth/refresh/**")
        )
    }
}
