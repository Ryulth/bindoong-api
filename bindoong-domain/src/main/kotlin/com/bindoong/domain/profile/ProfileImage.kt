package com.bindoong.domain.profile

import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.Id
import org.springframework.data.annotation.LastModifiedDate
import java.time.LocalDateTime

data class ProfileImage(
    @Id
    val profileImageId: String,
    val userId: String,
    val imageUrl: String,
    val thumbnailImageUrl: String,
    @CreatedDate
    val createdDateTime: LocalDateTime = LocalDateTime.now(),
    @LastModifiedDate
    val updatedDateTime: LocalDateTime = createdDateTime
)
