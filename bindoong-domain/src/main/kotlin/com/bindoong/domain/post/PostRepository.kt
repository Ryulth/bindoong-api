package com.bindoong.domain.post

import com.bindoong.domain.Cursor
import com.bindoong.domain.Cursorable
import org.springframework.stereotype.Repository

@Repository
interface PostRepository {
    suspend fun save(post: Post): Post
    suspend fun findById(postId: String): Post?
    suspend fun findAllByUserId(userId: String, cursorable: Cursorable): Cursor<Post>
    suspend fun deleteById(postId: String)
    suspend fun existsById(postId: String): Boolean
}
