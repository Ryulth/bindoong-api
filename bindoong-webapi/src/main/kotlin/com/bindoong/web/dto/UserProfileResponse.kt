package com.bindoong.web.dto

import com.bindoong.domain.profile.UserProfile

data class UserProfileResponse(
    val userId: String,
    val nickname: String
) {
    companion object {
        @JvmStatic
        operator fun invoke(userProfile: UserProfile) = UserProfileResponse(
            userId = userProfile.userId,
            nickname = userProfile.nickname
        )
    }
}
