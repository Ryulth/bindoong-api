package com.bindoong.service.post

import com.bindoong.domain.post.CreateParameter
import com.bindoong.domain.post.Post
import com.bindoong.domain.post.PostDomainService
import org.springframework.stereotype.Service

@Service
class PostService(
    private val postDomainService: PostDomainService
) {
    suspend fun create(parameter: PostCreateParameter) =
        postDomainService.create(
            CreateParameter(
                userId = parameter.userId, imageUrl = parameter.imageUrl
            )
        )

    suspend fun getOrThrow(postId: String): Post = postDomainService.get(postId) ?: throw IllegalArgumentException()
}

data class PostCreateParameter(
    val userId: Long,
    val imageUrl: String
)
