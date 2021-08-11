package com.bindoong.infrastructure.client

import kotlinx.coroutines.reactor.awaitSingleOrNull
import mu.KLogging
import org.springframework.http.HttpHeaders
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient

@Service
class KakaoApiClient(
    private val webClient: WebClient
) {
    suspend fun getUserInfo(accessToken: String) {
        webClient.get()
            .uri("$KAKAO_HOST_URL$USER_ME")
            .header(HttpHeaders.AUTHORIZATION, bearerToken(accessToken))
            .retrieve()
            .bodyToMono(String::class.java)
            .doOnSuccess { logger.debug { "Kakao user me success $it" } }
            .doOnError { throw it }
            .awaitSingleOrNull()
    }

    companion object : KLogging() {
        private val bearerToken = { accessToken: String -> "Bearer $accessToken" }
        private const val KAKAO_HOST_URL = "https://kapi.kakao.com"
        private const val USER_ME = "/v2/user/me"
    }
}
