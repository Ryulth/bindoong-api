package com.bindoong.web.security

import kotlinx.coroutines.reactor.mono
import mu.KLogging
import org.springframework.http.HttpHeaders
import org.springframework.security.core.Authentication
import org.springframework.web.server.ServerWebExchange
import reactor.core.publisher.Mono
import java.util.function.Function

class TokenAuthenticationConverter(
    private val tokenProvider: TokenProvider
) : Function<ServerWebExchange, Mono<Authentication>> {

    override fun apply(exchange: ServerWebExchange): Mono<Authentication> =
        mono {
            var authentication: Authentication? = null
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

    companion object : KLogging()
}
