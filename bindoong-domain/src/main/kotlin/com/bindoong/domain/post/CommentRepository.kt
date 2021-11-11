package com.bindoong.domain.post

import com.bindoong.domain.CursorPage
import com.bindoong.domain.CursorRequest
import org.springframework.stereotype.Repository

@Repository
interface CommentRepository {
    suspend fun insert(comment: Comment): Comment
    suspend fun update(comment: Comment): Comment
    suspend fun findById(commentId: String): Comment?
    suspend fun findAllByPostId(postId: String, cursorRequest: CursorRequest): CursorPage<Comment>
    suspend fun deleteById(commentId: String)
}
