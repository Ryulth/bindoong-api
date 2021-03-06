package com.bindoong.domain.user

import org.springframework.data.annotation.Id

data class FacebookUser(
    @Id
    val facebookId: String,
    val userId: String,
    var lastAccessToken: String
)
