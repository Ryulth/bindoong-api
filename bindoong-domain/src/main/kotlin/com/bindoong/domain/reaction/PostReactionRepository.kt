package com.bindoong.domain.reaction

import kotlinx.coroutines.flow.Flow
import org.springframework.stereotype.Repository

@Repository
interface PostReactionRepository {
    suspend fun insert(postReaction: PostReaction): PostReaction
    suspend fun update(postReaction: PostReaction): PostReaction
    suspend fun findById(postReactionId: String): PostReaction?
    suspend fun findAllByUserId(userId: String): Flow<PostReaction>
}
