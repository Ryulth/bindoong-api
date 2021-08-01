package com.bindoong.service.auth

import com.bindoong.domain.user.User
import com.bindoong.domain.user.UserDomainService

internal abstract class AbstractAuthService<T : RegisterParameter, S : LoginParameter>(
    private val userDomainService: UserDomainService
) {
    fun register(registerRequest: T): User {
        if (isExistAccount(registerRequest)) {
            throw AccountAlreadyExistException("Account already exist request: $registerRequest")
        }
        validateRequest(registerRequest)
        return createAccount(registerRequest)
    }

    protected abstract fun validateRequest(registerRequest: T)
    protected abstract fun isExistAccount(registerRequest: T): Boolean
    protected abstract fun createAccount(registerRequest: T): User

    fun login(loginRequest: S): User {
        return verifyAccount(loginRequest)
    }

    protected abstract fun verifyAccount(loginRequest: S): User

    suspend fun withDraw(userId: Long) {
        deleteAccount(userId)
        userDomainService.delete(userId)
    }

    protected abstract fun deleteAccount(userId: Long)
}