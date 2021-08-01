package com.bindoong.domain.user

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class FacebookUserDomainService(
    private val facebookUserRepository: FacebookUserRepository
) {
    @Transactional
    suspend fun create(parameter: FacebookUserCreateParameter, user: User): FacebookUser =
        this.save(
            FacebookUser(
                facebookId = parameter.facebookId,
                lastAccessToken = parameter.lastAccessToken,
                user = user
            )
        )

    @Transactional(readOnly = true)
    suspend fun get(facebookId: String): FacebookUser? = this.findByFacebookId(facebookId)

    @Transactional(readOnly = true)
    suspend fun isExist(facebookId: String): Boolean = this.existsByFacebookId(facebookId)

    @Transactional
    suspend fun delete(userId: Long) = this.deleteByUserId(userId)

    private suspend fun existsByFacebookId(facebookId: String): Boolean = facebookUserRepository.existsById(facebookId)

    private suspend fun save(facebookUser: FacebookUser): FacebookUser = facebookUserRepository.save(facebookUser)

    private suspend fun findByFacebookId(facebookId: String): FacebookUser? = facebookUserRepository.findById(facebookId)

    private suspend fun findByUserId(userId: Long): FacebookUser? = facebookUserRepository.findByUserId(userId)

    private suspend fun deleteByUserId(userId: Long) = facebookUserRepository.deleteByUserId(userId)
}