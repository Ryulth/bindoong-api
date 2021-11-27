package com.bindoong.domain.profile

data class UserProfile(
    val userId: String,
    val nickname: String,
    val imageUrl: String?,
    val thumbnailImageUrl: String?
)
