package com.bindoong.domain.user

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class KakaoUserDomainService(
    private val kakaoUserRepository: KakaoUserRepository
) {
    @Transactional
    suspend fun create(parameter: KakaoUserCreateParameter): KakaoUser =
        this.insert(
            KakaoUser(
                kakaoId = parameter.kakaoId,
                lastAccessToken = parameter.lastAccessToken,
                userId = parameter.userId
            )
        )

    @Transactional(readOnly = true)
    suspend fun get(kakaoId: String): KakaoUser? = this.findByKakaoId(kakaoId)

    @Transactional(readOnly = true)
    suspend fun isExist(kakaoId: String): Boolean = this.existsByKakaoId(kakaoId)

    @Transactional
    suspend fun delete(userId: String) = this.deleteByUserId(userId)

    private suspend fun existsByKakaoId(kakaoId: String): Boolean = kakaoUserRepository.existsById(kakaoId)

    private suspend fun insert(kakaoUser: KakaoUser): KakaoUser = kakaoUserRepository.insert(kakaoUser)

    private suspend fun findByKakaoId(kakaoId: String): KakaoUser? = kakaoUserRepository.findById(kakaoId)

    private suspend fun findByUserId(userId: String): KakaoUser? = kakaoUserRepository.findByUserId(userId)

    private suspend fun deleteByUserId(userId: String) = kakaoUserRepository.deleteByUserId(userId)
}

data class KakaoUserCreateParameter(
    val kakaoId: String,
    val userId: String,
    val lastAccessToken: String
)
