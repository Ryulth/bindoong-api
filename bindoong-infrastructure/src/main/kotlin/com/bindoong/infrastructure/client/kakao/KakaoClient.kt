package com.bindoong.infrastructure.client.kakao

import kotlinx.coroutines.reactor.awaitSingleOrNull
import org.springframework.stereotype.Service

@Service
class KakaoClient(
    private val kakaoAsyncClient: KakaoAsyncClient
) {
    suspend fun getUserInfo(accessToken: String) {
        kakaoAsyncClient.getUserInfo(accessToken).awaitSingleOrNull()
    }
}
