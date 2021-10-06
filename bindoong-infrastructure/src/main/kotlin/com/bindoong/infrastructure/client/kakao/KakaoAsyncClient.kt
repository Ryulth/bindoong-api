package com.bindoong.infrastructure.client.kakao

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker
import kotlinx.coroutines.reactor.awaitSingleOrNull
import kotlinx.coroutines.reactor.mono
import mu.KLogging
import org.springframework.http.HttpHeaders
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Mono

@Service
class KakaoAsyncClient(
    private val webClient: WebClient
) {
    @CircuitBreaker(name = "getUserInfo", fallbackMethod = "fallback")
    internal fun getUserInfo(accessToken: String): Mono<Void> {
        return mono {
            webClient.get()
                .uri("$KAKAO_HOST_URL$USER_ME")
                .header(HttpHeaders.AUTHORIZATION, bearerToken(accessToken))
                .retrieve()
                .bodyToMono(Void::class.java)
                .doOnSuccess { logger.debug { "Kakao user me success $it" } }
                .doOnError { throw it }
                .awaitSingleOrNull()
        }
    }

    fun fallback(e: Throwable): Mono<Void> {
        logger.error(e) { "KakaoApiClient fallback" }
        return Mono.error(e)
    }

    companion object : KLogging() {
        private val bearerToken = { accessToken: String -> "Bearer $accessToken" }
        private const val KAKAO_HOST_URL = "https://kapi.kakao.com"
        private const val USER_ME = "/v2/user/me"
    }
}
