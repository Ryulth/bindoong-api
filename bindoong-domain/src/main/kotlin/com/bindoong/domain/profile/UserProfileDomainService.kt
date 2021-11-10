package com.bindoong.domain.profile

import com.bindoong.domain.user.UserRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UserProfileDomainService(
    private val userRepository: UserRepository
) {
    @Transactional(readOnly = true)
    suspend fun get(userId: String): UserProfile? = userRepository.findById(userId)?.let { UserProfile(it) }

    @Transactional(readOnly = true)
    suspend fun duplicatedNickname(nickname: String): Boolean = userRepository.existsByNickname(nickname)
}
