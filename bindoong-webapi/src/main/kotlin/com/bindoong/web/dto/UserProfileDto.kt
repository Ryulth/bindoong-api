package com.bindoong.web.dto

import com.bindoong.domain.profile.UserProfile

data class UserProfileDto(
    val userId: String,
    val nickname: String,
    val imageUrl: String,
    val thumbnailImageUrl: String
) {
    companion object {
        @JvmStatic
        operator fun invoke(userProfile: UserProfile) = UserProfileDto(
            userId = userProfile.userId,
            nickname = userProfile.nickname,
            imageUrl = userProfile.imageUrl ?: "https://i.stack.imgur.com/34AD2.jpg",
            thumbnailImageUrl = userProfile.thumbnailImageUrl ?: "https://i.stack.imgur.com/34AD2.jpg"
        )
    }
}
