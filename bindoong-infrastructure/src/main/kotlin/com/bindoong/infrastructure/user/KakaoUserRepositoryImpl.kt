package com.bindoong.infrastructure.user

import com.bindoong.domain.user.KakaoUser
import com.bindoong.domain.user.KakaoUserRepository
import com.bindoong.domain.user.User
import kotlinx.coroutines.reactor.awaitSingle
import kotlinx.coroutines.reactor.awaitSingleOrNull
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate
import org.springframework.data.relational.core.query.Criteria.where
import org.springframework.data.relational.core.query.Query
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
class KakaoUserRepositoryImpl(
    private val template: R2dbcEntityTemplate
) : KakaoUserRepository {
    @Transactional
    override suspend fun save(kakaoUser: KakaoUser): KakaoUser =
        if (existsById(kakaoUser.kakaoId)) {
            template.update(kakaoUser).awaitSingle()
        } else {
            template.insert(kakaoUser).awaitSingle()
        }

    @Transactional
    override suspend fun existsById(kakaoId: String): Boolean =
        template.exists(Query.query(where(COLUMN_KAKAO_ID).`is`(kakaoId)), KakaoUser::class.java).awaitSingleOrNull()
            ?: false

    @Transactional
    override suspend fun findById(kakaoId: String): KakaoUser? =
        template.selectOne(Query.query(where(COLUMN_KAKAO_ID).`is`(kakaoId)), KakaoUser::class.java).awaitSingleOrNull()

    @Transactional
    override suspend fun findByUserId(userId: Long): KakaoUser? =
        template.selectOne(Query.query(where(COLUMN_USER_ID).`is`(userId)), KakaoUser::class.java).awaitSingleOrNull()

    @Transactional
    override suspend fun deleteByUserId(userId: Long) {
        template.delete(Query.query(where(COLUMN_USER_ID).`is`(userId)), User::class.java).awaitSingleOrNull()
    }

    companion object {
        private const val COLUMN_KAKAO_ID = "kakao_id"
        private const val COLUMN_USER_ID = "user_id"
    }
}
