package com.bindoong.infrastructure.client.kakao

import kotlinx.coroutines.reactor.awaitSingleOrNull
import org.springframework.stereotype.Service

@Service
class KakaoClient(
    private val kakaoApiClient: KakaoAsyncClient
) {
    suspend fun getUserInfo(accessToken: String) {
        kakaoApiClient.getUserInfo(accessToken).awaitSingleOrNull()
    }
}
