package com.bindoong.web.dto

import java.time.LocalDateTime

data class NotificationDto(
    val sender: UserProfileDto,
    val content: String,
    val createDateTime: LocalDateTime
)
