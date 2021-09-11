package com.bindoong.domain.post

import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.Id
import org.springframework.data.annotation.LastModifiedDate
import java.time.LocalDateTime

data class Post(
    @Id
    val postId: String,
    val userId: String,
    val imageUrl: String,
    val content: String?,
    @CreatedDate
    val createdDateTime: LocalDateTime = LocalDateTime.now(),
    @LastModifiedDate
    val updatedDateTime: LocalDateTime = createdDateTime
)
