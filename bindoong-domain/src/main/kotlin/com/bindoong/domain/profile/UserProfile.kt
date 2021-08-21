package com.bindoong.domain.profile

import com.bindoong.domain.user.User

data class UserProfile(
    val userId: String,
    val nickname: String,
) {
    companion object {
        @JvmStatic
        operator fun invoke(user: User) = UserProfile(
            userId = user.userId,
            nickname = user.nickname
        )
    }
}
