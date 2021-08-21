package com.bindoong.web.dto

import com.bindoong.domain.post.Post

data class PostDto(
    val postId: String,
    val imageUrl: String
) {
    companion object {
        @JvmStatic
        operator fun invoke(post: Post) = PostDto(
            postId = post.postId,
            imageUrl = post.imageUrl
        )
    }
}
