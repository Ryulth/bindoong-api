package com.bindoong.domain.user

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class FacebookUserDomainService(
    private val facebookUserRepository: FacebookUserRepository
) {
    @Transactional
    suspend fun create(parameter: FacebookUserCreateParameter): FacebookUser =
        this.insert(
            FacebookUser(
                facebookId = parameter.facebookId,
                lastAccessToken = parameter.lastAccessToken,
                userId = parameter.userId
            )
        )

    @Transactional(readOnly = true)
    suspend fun get(facebookId: String): FacebookUser? = this.findByFacebookId(facebookId)

    @Transactional(readOnly = true)
    suspend fun isExist(facebookId: String): Boolean = this.existsByFacebookId(facebookId)

    @Transactional
    suspend fun delete(userId: String) = this.deleteByUserId(userId)

    private suspend fun existsByFacebookId(facebookId: String): Boolean = facebookUserRepository.existsById(facebookId)

    private suspend fun insert(facebookUser: FacebookUser): FacebookUser = facebookUserRepository.insert(facebookUser)

    private suspend fun findByFacebookId(facebookId: String): FacebookUser? =
        facebookUserRepository.findById(facebookId)

    private suspend fun findByUserId(userId: String): FacebookUser? = facebookUserRepository.findByUserId(userId)

    private suspend fun deleteByUserId(userId: String) = facebookUserRepository.deleteByUserId(userId)
}

data class FacebookUserCreateParameter(
    val facebookId: String,
    val userId: String,
    val lastAccessToken: String
)
