package com.bindoong.domain.profile

import com.bindoong.domain.user.UserRepository
import com.github.f4b6a3.ulid.UlidCreator
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UserProfileDomainService(
    private val userRepository: UserRepository,
    private val profileImageRepository: ProfileImageRepository
) {
    @Transactional(readOnly = true)
    suspend fun get(userId: String): UserProfile? {
        return userRepository.findById(userId)?.let { user ->
            val profileImage = profileImageRepository.findByUserId(user.userId)
            UserProfile(
                userId = user.userId,
                nickname = user.nickname,
                imageUrl = profileImage?.imageUrl,
                thumbnailImageUrl = profileImage?.thumbnailImageUrl
            )
        }
    }

    @Transactional
    suspend fun saveImage(userId: String, imageUrl: String, thumbnailImageUrl: String) {
        profileImageRepository.deleteByUserId(userId)
        profileImageRepository.insert(
            ProfileImage(
                profileImageId = UlidCreator.getUlid().toString(),
                userId = userId,
                imageUrl = imageUrl,
                thumbnailImageUrl = thumbnailImageUrl
            )
        )
    }

    @Transactional(readOnly = true)
    suspend fun duplicatedNickname(nickname: String): Boolean = userRepository.existsByNickname(nickname)
}
