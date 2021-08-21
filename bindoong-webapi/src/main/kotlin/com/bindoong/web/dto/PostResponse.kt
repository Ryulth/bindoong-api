package com.bindoong.web.dto

import com.bindoong.domain.post.Post

data class PostResponse(
    val postId: String,
    val imageUrl: String
) {
    companion object {
        @JvmStatic
        operator fun invoke(post: Post) = PostResponse(
            postId = post.postId,
            imageUrl = post.imageUrl
        )
    }
}
