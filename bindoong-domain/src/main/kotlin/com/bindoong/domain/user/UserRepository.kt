package com.bindoong.domain.user

import org.springframework.stereotype.Repository

@Repository
interface UserRepository {
    suspend fun insert(user: User): User
    suspend fun update(user: User): User
    suspend fun findById(userId: String): User?
    suspend fun deleteById(userId: String)
    suspend fun existsById(userId: String): Boolean
}
