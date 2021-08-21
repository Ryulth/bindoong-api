package com.bindoong.infrastructure.user

import com.bindoong.domain.user.FacebookUser
import com.bindoong.domain.user.FacebookUserRepository
import com.bindoong.domain.user.User
import kotlinx.coroutines.reactor.awaitSingle
import kotlinx.coroutines.reactor.awaitSingleOrNull
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate
import org.springframework.data.relational.core.query.Criteria.where
import org.springframework.data.relational.core.query.Query
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
class FacebookUserRepositoryImpl(
    private val template: R2dbcEntityTemplate
) : FacebookUserRepository {
    @Transactional
    override suspend fun save(facebookUser: FacebookUser): FacebookUser =
        if (existsById(facebookUser.facebookId)) {
            template.update(facebookUser).awaitSingle()
        } else {
            template.insert(facebookUser).awaitSingle()
        }

    @Transactional
    override suspend fun existsById(facebookId: String): Boolean =
        template.exists(Query.query(where(COLUMN_FACEBOOK_ID).`is`(facebookId)), FacebookUser::class.java)
            .awaitSingleOrNull()
            ?: false

    @Transactional
    override suspend fun findById(facebookId: String): FacebookUser? =
        template.selectOne(Query.query(where(COLUMN_FACEBOOK_ID).`is`(facebookId)), FacebookUser::class.java)
            .awaitSingleOrNull()

    @Transactional
    override suspend fun findByUserId(userId: String): FacebookUser? =
        template.selectOne(Query.query(where(COLUMN_USER_ID).`is`(userId)), FacebookUser::class.java)
            .awaitSingleOrNull()

    @Transactional
    override suspend fun deleteByUserId(userId: String) {
        template.delete(Query.query(where(COLUMN_USER_ID).`is`(userId)), User::class.java).awaitSingleOrNull()
    }

    companion object {
        private const val COLUMN_FACEBOOK_ID = "facebook_id"
        private const val COLUMN_USER_ID = "user_id"
    }
}
