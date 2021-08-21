package com.bindoong.domain.post

import kotlinx.coroutines.flow.Flow
import org.springframework.stereotype.Repository

@Repository
interface PostRepository {
    suspend fun save(post: Post): Post
    suspend fun findById(postId: String): Post?
    suspend fun findAllByUserId(userId: String): Flow<Post>
    suspend fun deleteById(postId: String)
    suspend fun existsById(postId: String): Boolean
}
