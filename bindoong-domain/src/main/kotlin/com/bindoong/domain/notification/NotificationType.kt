package com.bindoong.domain.notification

enum class NotificationType(
    val messageTemplate: String
) {
    SEND_HEART("%s 님이 %s 에게 보냄")
}