package com.bindoong.domain.user

import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.Id
import org.springframework.data.annotation.LastModifiedDate
import java.time.LocalDateTime
import java.util.UUID

class User(
    @Id
    val id: Long? = null,
    val uuid: String = UUID.randomUUID().toString().replace("-", ""),
    val nickName: String,
    val loginType: LoginType,
    val roles: Set<Role>,
    @CreatedDate
    val createdDateTime: LocalDateTime = LocalDateTime.now(),
    @LastModifiedDate
    val updatedDateTime: LocalDateTime = createdDateTime
)