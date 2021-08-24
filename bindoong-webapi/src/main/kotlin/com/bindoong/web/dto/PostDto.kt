package com.bindoong.web.dto

import com.bindoong.domain.post.Post
import java.time.LocalDateTime

data class PostDto(
    val postId: String,
    val imageUrl: String,
    val createDateTime: LocalDateTime
) {
    companion object {
        @JvmStatic
        operator fun invoke(post: Post) = PostDto(
            postId = post.postId,
            imageUrl = post.imageUrl,
            createDateTime = post.createdDateTime
        )
    }
}
