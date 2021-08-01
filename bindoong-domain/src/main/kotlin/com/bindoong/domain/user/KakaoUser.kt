package com.bindoong.domain.user

import org.springframework.data.annotation.Id

class KakaoUser(
    @Id
    val kakaoId: String,
    val userId: Long,
    var lastAccessToken: String
)