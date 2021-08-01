package com.bindoong.domain.user

import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import org.springframework.stereotype.Repository

@Repository
interface KakaoUserRepository: CoroutineCrudRepository<KakaoUser, String> {
    suspend fun findByUserId(userId: Long): KakaoUser?
    suspend fun deleteByUserId(userId: Long)
}