package com.bindoong.domain.notification

import org.springframework.data.annotation.Id

data class Notification(
    @Id
    val notificationId: String,
    val senderUserId: String,
    val receiverUserId: String,
    val notificationType: NotificationType,
    var ack: Boolean
)
