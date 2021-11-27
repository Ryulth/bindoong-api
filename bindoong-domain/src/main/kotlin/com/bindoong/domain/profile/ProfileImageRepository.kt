package com.bindoong.domain.profile

import org.springframework.stereotype.Repository

@Repository
interface ProfileImageRepository {
    suspend fun insert(profileImage: ProfileImage): ProfileImage
    suspend fun update(profileImage: ProfileImage): ProfileImage
    suspend fun findById(profileImageId: String): ProfileImage?
    suspend fun findByUserId(userId: String): ProfileImage?
    suspend fun deleteById(profileImageId: String)
    suspend fun deleteByUserId(userId: String)
}
