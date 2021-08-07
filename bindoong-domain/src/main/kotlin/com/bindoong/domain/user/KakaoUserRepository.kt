package com.bindoong.domain.user

import org.springframework.stereotype.Repository

@Repository
interface KakaoUserRepository {
    suspend fun save(kakaoUser: KakaoUser): KakaoUser
    suspend fun existsById(kakaoId: String): Boolean
    suspend fun findById(kakaoId: String): KakaoUser?
    suspend fun findByUserId(userId: Long): KakaoUser?
    suspend fun deleteByUserId(userId: Long)
}
