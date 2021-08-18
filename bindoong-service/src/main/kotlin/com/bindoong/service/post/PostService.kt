package com.bindoong.service.post

import com.bindoong.domain.post.CreateParameter
import com.bindoong.domain.post.Post
import com.bindoong.domain.post.PostDomainService
import com.bindoong.domain.post.UpdateParameter
import org.springframework.stereotype.Service

@Service
class PostService(
    private val postDomainService: PostDomainService
) {
    suspend fun create(parameter: PostCreateParameter): Post =
        postDomainService.create(
            parameter.toParameter()
        )

    suspend fun getOrThrow(postId: String): Post = postDomainService.get(postId) ?: throw IllegalArgumentException()

    suspend fun update(parameter: PostUpdateParameter): Post =
        getOrThrow(parameter.postId).takeIf { it.userId == parameter.userId }?.let {
            postDomainService.update(
                parameter.toParameter()
            )
        } ?: throw IllegalArgumentException("Not my post")

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
    val userId: Long,
    val imageUrl: String
)

data class PostUpdateParameter(
    val userId: Long,
    val postId: String,
    val imageUrl: String
)
