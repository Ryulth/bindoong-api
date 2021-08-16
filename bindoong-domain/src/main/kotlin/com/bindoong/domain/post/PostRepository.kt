package com.bindoong.domain.post

import org.springframework.stereotype.Repository

@Repository
interface PostRepository {
    suspend fun save(post: Post): Post
    suspend fun findById(postId: String): Post?
    suspend fun findByUserId(userId: Long): Post?
    suspend fun deleteById(postId: String)
    suspend fun existsById(postId: String): Boolean
}
