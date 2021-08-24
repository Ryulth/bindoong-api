package com.bindoong.domain.user

import org.springframework.stereotype.Repository

@Repository
interface KakaoUserRepository {
    suspend fun insert(kakaoUser: KakaoUser): KakaoUser
    suspend fun update(kakaoUser: KakaoUser): KakaoUser
    suspend fun existsById(kakaoId: String): Boolean
    suspend fun findById(kakaoId: String): KakaoUser?
    suspend fun findByUserId(userId: String): KakaoUser?
    suspend fun deleteByUserId(userId: String)
}
