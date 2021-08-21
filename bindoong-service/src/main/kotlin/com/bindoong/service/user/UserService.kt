package com.bindoong.service.user

import com.bindoong.domain.user.UserDomainService
import org.springframework.stereotype.Service

@Service
class UserService(
    private val userDomainService: UserDomainService
) {
    suspend fun withDraw(userId: String) = userDomainService.delete(userId)
}
