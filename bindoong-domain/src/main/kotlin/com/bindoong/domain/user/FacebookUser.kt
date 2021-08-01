package com.bindoong.domain.user

import org.springframework.data.annotation.Id

class FacebookUser(
    @Id
    val facebookId: String,
    var lastAccessToken: String,
    val user: User
)