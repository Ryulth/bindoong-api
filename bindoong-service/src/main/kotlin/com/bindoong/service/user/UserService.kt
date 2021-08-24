package com.bindoong.service.user

import com.bindoong.domain.user.UserDomainService
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UserService(
    private val userDomainService: UserDomainService
) {
    @Transactional
    suspend fun withDraw(userId: String) = userDomainService.delete(userId)
}
