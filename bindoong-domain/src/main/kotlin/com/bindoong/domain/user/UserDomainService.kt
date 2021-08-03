package com.bindoong.domain.user

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UserDomainService(
    private val userRepository: UserRepository
) {
    @Transactional
    suspend fun create(parameter: UserCreateParameter): User =
        this.save(
            User(
                nickName = parameter.nickName,
                loginType = parameter.loginType,
                roles = parameter.roles
            )
        )

    @Transactional(readOnly = true)
    suspend fun get(userId: Long): User? = this.findByUserId(userId)

    @Transactional
    suspend fun delete(userId: Long) = this.deleteByUserId(userId)

    private suspend fun save(user: User): User = userRepository.save(user)

    private suspend fun findByUserId(userId: Long): User? = userRepository.findById(userId)

    private suspend fun deleteByUserId(userId: Long) = userRepository.deleteById(userId)
}