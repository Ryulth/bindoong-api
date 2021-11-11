package com.bindoong.service.post

import com.bindoong.core.exceptions.UserNotAllowedException
import com.bindoong.domain.CursorPage
import com.bindoong.domain.CursorRequest
import com.bindoong.domain.post.Comment
import com.bindoong.domain.post.CommentDomainService
import com.bindoong.domain.post.CommentParameter
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class CommentService(
    private val commentDomainService: CommentDomainService
) {
    @Transactional
    suspend fun create(parameter: CommentCreateParameter): Comment =
        commentDomainService.create(
            parameter.toParameter()
        )

    @Transactional
    suspend fun getOrThrow(commentId: String): Comment =
        commentDomainService.getByCommentId(commentId) ?: throw IllegalArgumentException()

    @Transactional
    suspend fun getAll(postId: String, cursorRequest: CursorRequest): CursorPage<Comment> =
        commentDomainService.getAllByPostId(postId, cursorRequest)

    @Transactional
    suspend fun update(parameter: CommentUpdateParameter): Comment =
        getOrThrow(parameter.commentId).takeIf { it.userId == parameter.userId }?.let {
            commentDomainService.update(
                parameter.commentId, parameter.toParameter()
            )
        } ?: throw UserNotAllowedException("Not my post")

    @Transactional
    suspend fun delete(commentId: String) = commentDomainService.delete(commentId)

    private fun CommentCreateParameter.toParameter() = CommentParameter(
        userId = userId,
        postId = postId,
        content = content
    )

    private fun CommentUpdateParameter.toParameter() = CommentParameter(
        userId = userId,
        postId = postId,
        content = content
    )
}

data class CommentCreateParameter(
    val userId: String,
    val postId: String,
    val content: String,
)

data class CommentUpdateParameter(
    val commentId: String,
    val userId: String,
    val postId: String,
    val content: String
)
