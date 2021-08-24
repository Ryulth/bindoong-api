package com.bindoong.domain.user

import com.bindoong.core.utils.StringUtils.toBase64String
import com.github.f4b6a3.uuid.UuidCreator
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UserDomainService(
    private val userRepository: UserRepository
) {
    @Transactional
    suspend fun create(parameter: UserCreateParameter): User =
        this.insert(
            User(
                userId = UuidCreator.getRandomBased().toBase64String(),
                nickname = parameter.nickName,
                loginType = parameter.loginType,
                roles = parameter.roles.first()
            )
        )

    @Transactional(readOnly = true)
    suspend fun get(userId: String): User? = this.findByUserId(userId)

    @Transactional
    suspend fun delete(userId: String) = this.deleteByUserId(userId)

    private suspend fun insert(user: User): User = userRepository.insert(user)

    private suspend fun findByUserId(userId: String): User? = userRepository.findById(userId)

    private suspend fun deleteByUserId(userId: String) = userRepository.deleteById(userId)
}

data class UserCreateParameter(
    val nickName: String,
    val loginType: LoginType,
    val roles: Set<Role>
)
