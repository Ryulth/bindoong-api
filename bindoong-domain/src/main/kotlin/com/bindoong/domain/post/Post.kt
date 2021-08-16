package com.bindoong.domain.post

import org.springframework.data.annotation.Id

data class Post(
    @Id
    val postId: String,
    val userId: Long,
    val imageUrl: String
)
