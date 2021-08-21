package com.bindoong.domain.user

import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import java.time.LocalDateTime

data class User(
    val userId: String,
    val nickname: String,
    val loginType: LoginType,
    val roles: Role,
    @CreatedDate
    val createdDateTime: LocalDateTime = LocalDateTime.now(),
    @LastModifiedDate
    val updatedDateTime: LocalDateTime = createdDateTime
)
