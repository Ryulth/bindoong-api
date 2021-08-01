package com.bindoong.domain.user

import org.springframework.data.annotation.Id

class KakaoUser(
    @Id
    val kakaoId: String,
    var lastAccessToken: String,
    val user: User
)