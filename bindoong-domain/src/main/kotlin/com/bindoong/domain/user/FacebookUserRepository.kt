package com.bindoong.domain.user

import org.springframework.stereotype.Repository

@Repository
interface FacebookUserRepository {
    suspend fun insert(facebookUser: FacebookUser): FacebookUser
    suspend fun update(facebookUser: FacebookUser): FacebookUser
    suspend fun existsById(facebookId: String): Boolean
    suspend fun findById(facebookId: String): FacebookUser?
    suspend fun findByUserId(userId: String): FacebookUser?
    suspend fun deleteByUserId(userId: String)
}
