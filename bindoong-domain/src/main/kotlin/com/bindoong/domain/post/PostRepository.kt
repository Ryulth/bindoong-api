package com.bindoong.domain.post

import com.bindoong.domain.CursorPage
import com.bindoong.domain.CursorRequest
import org.springframework.stereotype.Repository

@Repository
interface PostRepository {
    suspend fun save(post: Post): Post
    suspend fun findById(postId: String): Post?
    suspend fun findAllByUserId(userId: String, cursorRequest: CursorRequest): CursorPage<Post>
    suspend fun deleteById(postId: String)
    suspend fun existsById(postId: String): Boolean
}
