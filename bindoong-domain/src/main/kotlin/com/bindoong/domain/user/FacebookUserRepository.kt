package com.bindoong.domain.user

import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import org.springframework.stereotype.Repository

@Repository
interface FacebookUserRepository: CoroutineCrudRepository<FacebookUser, String> {
    suspend fun findByUserId(userId: Long): FacebookUser?
    suspend fun deleteByUserId(userId: Long)
}