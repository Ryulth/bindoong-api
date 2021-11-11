package com.bindoong.web.dto

import com.bindoong.domain.post.Comment
import java.time.LocalDateTime

data class CommentDto(
    val commentId: String,
    val userId: String,
    val postId: String,
    val content: String,
    val createDateTime: LocalDateTime
) {
    companion object {
        @JvmStatic
        operator fun invoke(comment: Comment) = CommentDto(
            commentId = comment.commentId,
            userId = comment.userId,
            postId = comment.postId,
            content = comment.content,
            createDateTime = comment.createdDateTime,
        )
    }
}
