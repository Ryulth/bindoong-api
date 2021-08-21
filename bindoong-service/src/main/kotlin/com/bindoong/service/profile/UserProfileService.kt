package com.bindoong.service.profile

import com.bindoong.domain.profile.UserProfile
import com.bindoong.domain.profile.UserProfileDomainService
import com.bindoong.service.user.UserNotFoundException
import org.springframework.stereotype.Service

@Service
class UserProfileService(
    private val userProfileDomainService: UserProfileDomainService
) {
    suspend fun getUserProfile(userId: String): UserProfile =
        userProfileDomainService.get(userId) ?: throw UserNotFoundException()
}
