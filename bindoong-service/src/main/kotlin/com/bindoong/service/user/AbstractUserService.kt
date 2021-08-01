package com.bindoong.service.user

import com.bindoong.domain.user.User

abstract class AbstractUserService<T : RegisterParameter, S : LoginParameter> {
    suspend fun register(registerParameter: T): User {
        if (isExist(registerParameter)) {
            throw UserAlreadyExistException("User already exist request: $registerParameter")
        }
        registerParameter.nickname.run {
            require(this.isBlank()) { "Register nickname is invalid" }
        }
        validateRegister(registerParameter)
        return create(registerParameter)
    }

    suspend fun login(loginParameter: S): User {
        return get(loginParameter)
    }

    suspend fun withDraw(userId: Long) {
        delete(userId)
    }

    protected abstract suspend fun validateRegister(registerParameter: T)
    protected abstract suspend fun get(loginParameter: S): User
    protected abstract suspend fun isExist(registerParameter: T): Boolean
    protected abstract suspend fun create(registerParameter: T): User
    protected abstract suspend fun delete(userId: Long)
}