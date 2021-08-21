package com.bindoong.service.user

import com.bindoong.domain.user.User
import com.bindoong.domain.user.UserDomainService
import org.springframework.stereotype.Service

@Service
class UserService(
    private val userDomainService: UserDomainService
) {
    suspend fun getOrThrow(userId: String): User = userDomainService.get(userId) ?: throw IllegalArgumentException()

    suspend fun withDraw(userId: String) = userDomainService.delete(userId)
}
