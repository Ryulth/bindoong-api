package com.bindoong.domain.user

data class FacebookUser(
    val facebookId: String,
    val userId: String,
    var lastAccessToken: String
)
