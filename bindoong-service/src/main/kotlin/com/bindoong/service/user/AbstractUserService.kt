package com.bindoong.service.user

import com.bindoong.domain.user.User
import org.springframework.transaction.annotation.Transactional

abstract class AbstractUserService<T : RegisterParameter, S : LoginParameter> {
    @Transactional
    open suspend fun register(registerParameter: T): User {
        if (isExist(registerParameter)) {
            throw UserAlreadyExistException("User already exist request: $registerParameter")
        }
        registerParameter.nickname.run {
            require(this.isNotEmpty()) { "Register nickname $this is invalid" }
        }
        validateRegister(registerParameter)
        return doRegister(registerParameter)
    }

    @Transactional
    open suspend fun login(loginParameter: S): User {
        validateLogin(loginParameter)
        return doLogin(loginParameter)
    }

    @Transactional
    open suspend fun withDraw(userId: String) {
        doWithDraw(userId)
    }

    protected abstract suspend fun doRegister(registerParameter: T): User
    protected abstract suspend fun validateRegister(registerParameter: T)
    protected abstract suspend fun doLogin(loginParameter: S): User
    protected abstract suspend fun validateLogin(loginParameter: S)
    protected abstract suspend fun isExist(registerParameter: T): Boolean
    protected abstract suspend fun doWithDraw(userId: String)
}

sealed class RegisterParameter(
    open val nickname: String
)

sealed class LoginParameter
