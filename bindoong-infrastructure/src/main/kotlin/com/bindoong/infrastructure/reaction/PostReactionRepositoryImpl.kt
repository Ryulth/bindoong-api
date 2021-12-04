package com.bindoong.infrastructure.reaction

import com.bindoong.domain.reaction.PostReaction
import com.bindoong.domain.reaction.PostReactionRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.reactive.asFlow
import kotlinx.coroutines.reactor.awaitSingle
import kotlinx.coroutines.reactor.awaitSingleOrNull
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate
import org.springframework.data.relational.core.query.Criteria
import org.springframework.data.relational.core.query.Query
import org.springframework.stereotype.Component

@Component
class PostReactionRepositoryImpl(
    private val template: R2dbcEntityTemplate
) : PostReactionRepository {
    override suspend fun insert(postReaction: PostReaction): PostReaction {
        return template.insert(postReaction).awaitSingle()
    }

    override suspend fun update(postReaction: PostReaction): PostReaction {
        return template.update(postReaction).awaitSingle()
    }

    override suspend fun findById(postReactionId: String): PostReaction? {
        return template.selectOne(
            Query.query(Criteria.where(COLUMN_POST_REACTION_ID).`is`(postReactionId)),
            PostReaction::class.java
        ).awaitSingleOrNull()
    }

    override suspend fun findAllByUserId(userId: String): Flow<PostReaction> {
        return template.select(
            Query.query(Criteria.where(COLUMN_USER_ID).`is`(userId)),
            PostReaction::class.java
        ).asFlow()
    }

    companion object {
        private const val COLUMN_POST_REACTION_ID = "post_reaction_id"
        private const val COLUMN_USER_ID = "user_id"
    }
}
