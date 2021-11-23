package com.bindoong.service.notification

import com.bindoong.core.exceptions.UserNotAllowedException
import com.bindoong.domain.CursorPage
import com.bindoong.domain.CursorRequest
import com.bindoong.domain.notification.Notification
import com.bindoong.domain.notification.NotificationDomainService
import com.bindoong.domain.notification.UpdateParameter
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class NotificationService(
    private val notificationDomainService: NotificationDomainService
) {
    @Transactional
    suspend fun getOrThrow(notificationId: String): Notification =
        notificationDomainService.getByNotificationId(notificationId) ?: throw IllegalArgumentException()

    @Transactional
    suspend fun getAll(userId: String, cursorRequest: CursorRequest): CursorPage<Notification> =
        notificationDomainService.getAllByUserId(userId, cursorRequest)

    @Transactional
    suspend fun ack(userId: String, notificationId: String): Notification =
        getOrThrow(notificationId).takeIf { it.receiverUserId == userId }?.let {
            notificationDomainService.update(
                UpdateParameter(
                    notificationId = it.notificationId,
                    senderUserId = it.senderUserId,
                    receiverUserId = it.receiverUserId,
                    notificationType = it.notificationType,
                    ack = true
                )
            )
        } ?: throw UserNotAllowedException("Not my notification")
}
