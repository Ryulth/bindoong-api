package com.bindoong.domain.post

import com.bindoong.domain.CursorPage
import com.bindoong.domain.CursorRequest
import com.github.f4b6a3.ulid.UlidCreator
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class CommentDomainService(
    private val commentRepository: CommentRepository
) {
    @Transactional
    suspend fun create(parameter: CommentParameter): Comment =
        commentRepository.insert(
            Comment(
                commentId = UlidCreator.getUlid().toString(),
                userId = parameter.userId,
                postId = parameter.postId,
                content = parameter.content
            )
        )

    @Transactional
    suspend fun update(commentId: String, parameter: CommentParameter): Comment =
        commentRepository.update(
            Comment(
                commentId = commentId,
                userId = parameter.userId,
                postId = parameter.postId,
                content = parameter.content
            )
        )

    @Transactional(readOnly = true)
    suspend fun getByCommentId(commentId: String): Comment? = commentRepository.findById(commentId)

    @Transactional(readOnly = true)
    suspend fun getAllByPostId(postId: String, cursorRequest: CursorRequest): CursorPage<Comment> =
        commentRepository.findAllByPostId(postId, cursorRequest)

    @Transactional
    suspend fun delete(commentId: String) = commentRepository.deleteById(commentId)
}

data class CommentParameter(
    val userId: String,
    val postId: String,
    val content: String,
)
