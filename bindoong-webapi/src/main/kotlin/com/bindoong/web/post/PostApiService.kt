package com.bindoong.web.post

import com.bindoong.service.post.PostCreateParameter
import com.bindoong.service.post.PostService
import org.springframework.stereotype.Service

@Service
class PostApiService(
    private val postService: PostService
) {
    suspend fun createPost(registerRequest: PostCreateRequest) {
        val userId = 1L
        postService.create(registerRequest.toCreateParameter(userId))
    }

    private fun PostCreateRequest.toCreateParameter(userId: Long) = PostCreateParameter(
        userId = userId,
        imageUrl = imageUrl
    )
}

data class PostCreateRequest(
    val imageUrl: String
)
