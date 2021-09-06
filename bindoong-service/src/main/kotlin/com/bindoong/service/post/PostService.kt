package com.bindoong.service.post

import com.bindoong.core.exceptions.UserNotAllowedException
import com.bindoong.domain.CursorPage
import com.bindoong.domain.CursorRequest
import com.bindoong.domain.post.CreateParameter
import com.bindoong.domain.post.Post
import com.bindoong.domain.post.PostDomainService
import com.bindoong.domain.post.UpdateParameter
import kotlinx.coroutines.flow.Flow
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class PostService(
    private val postDomainService: PostDomainService
) {
    @Transactional
    suspend fun create(parameter: PostCreateParameter): Post =
        postDomainService.create(
            parameter.toParameter()
        )

    @Transactional
    suspend fun getOrThrow(postId: String): Post =
        postDomainService.getByPostId(postId) ?: throw IllegalArgumentException()

    @Transactional
    suspend fun getAll(userId: String, cursorRequest: CursorRequest): CursorPage<Post> =
        postDomainService.getAllByUserId(userId, cursorRequest)

    @Transactional
    suspend fun getRandom(userId: String): Flow<Post> =
        postDomainService.getAllByRandomExcludeUserId(5, userId)

    @Transactional
    suspend fun update(parameter: PostUpdateParameter): Post =
        getOrThrow(parameter.postId).takeIf { it.userId == parameter.userId }?.let {
            postDomainService.update(
                parameter.toParameter()
            )
        } ?: throw UserNotAllowedException("Not my post")

    @Transactional
    suspend fun delete(postId: String) = postDomainService.delete(postId)

    private fun PostCreateParameter.toParameter() = CreateParameter(
        userId = userId,
        imageUrl = imageUrl
    )

    private fun PostUpdateParameter.toParameter() = UpdateParameter(
        userId = userId,
        postId = postId,
        imageUrl = imageUrl
    )
}

data class PostCreateParameter(
    val userId: String,
    val imageUrl: String
)

data class PostUpdateParameter(
    val userId: String,
    val postId: String,
    val imageUrl: String
)
