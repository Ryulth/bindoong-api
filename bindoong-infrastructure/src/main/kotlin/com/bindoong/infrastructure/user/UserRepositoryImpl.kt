package com.bindoong.infrastructure.user

import com.bindoong.domain.user.User
import com.bindoong.domain.user.UserRepository
import kotlinx.coroutines.reactor.awaitSingle
import kotlinx.coroutines.reactor.awaitSingleOrNull
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate
import org.springframework.data.relational.core.query.Criteria.where
import org.springframework.data.relational.core.query.Query
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
class UserRepositoryImpl(
    private val template: R2dbcEntityTemplate
) : UserRepository {
    @Transactional
    override suspend fun save(user: User): User =
        if (user.userId?.let { existsById(it) } == true) {
            template.update(user).awaitSingle()
        } else {
            template.insert(user).awaitSingle()
        }

    @Transactional
    override suspend fun existsById(userId: Long): Boolean =
        template.exists(Query.query(where(COLUMN_USER_ID).`is`(userId)), User::class.java).awaitSingleOrNull()
            ?: false

    @Transactional
    override suspend fun findById(userId: Long): User? =
        template.selectOne(Query.query(where(COLUMN_USER_ID).`is`(userId)), User::class.java).awaitSingleOrNull()

    @Transactional
    override suspend fun deleteById(userId: Long) {
        template.delete(Query.query(where(COLUMN_USER_ID).`is`(userId)), User::class.java).awaitSingleOrNull()
    }

    companion object {
        private const val COLUMN_USER_ID = "user_id"
    }
}
