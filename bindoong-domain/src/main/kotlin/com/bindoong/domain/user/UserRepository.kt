package com.bindoong.domain.user

import org.springframework.stereotype.Repository

@Repository
interface UserRepository {
    suspend fun save(user: User): User
    suspend fun findById(userId: Long): User?
    suspend fun deleteById(userId: Long)
    suspend fun existsById(userId: Long): Boolean
}
