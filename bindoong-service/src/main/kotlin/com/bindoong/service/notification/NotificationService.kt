package com.bindoong.service.notification

import com.bindoong.core.exceptions.UserNotAllowedException
import com.bindoong.domain.CursorPage
import com.bindoong.domain.CursorRequest
import com.bindoong.domain.notification.CreateParameter
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
    suspend fun create(parameter: NotificationCreateParameter): Notification =
        notificationDomainService.create(
            parameter.toParameter()
        )

    @Transactional
    suspend fun getOrThrow(notificationId: String): Notification =
        notificationDomainService.getByNotificationId(notificationId) ?: throw IllegalArgumentException()

    @Transactional
    suspend fun getAll(userId: String, cursorRequest: CursorRequest): CursorPage<Notification> =
        notificationDomainService.getAllByUserId(userId, cursorRequest)

    @Transactional
    suspend fun update(parameter: NotificationUpdateParameter): Notification =
        getOrThrow(parameter.notificationId).takeIf { it.receiverUserId == parameter.userId }?.let {
            notificationDomainService.update(
                parameter.toParameter()
            )
        } ?: throw UserNotAllowedException("Not my notification")

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

    @Transactional
    suspend fun delete(notificationId: String) = notificationDomainService.delete(notificationId)

    private fun NotificationCreateParameter.toParameter() = CreateParameter(
        userId = userId,
        imageUrl = imageUrl,
        content = content,
        locationId = locationId
    )

    private fun NotificationUpdateParameter.toParameter() = UpdateParameter(
        userId = userId,
        notificationId = notificationId,
        imageUrl = imageUrl,
        content = content,
        locationId = locationId
    )
}

data class NotificationCreateParameter(
    val userId: String,
    val imageUrl: String,
    val content: String?,
    val locationId: String?
)

data class NotificationUpdateParameter(
    val userId: String,
    val notificationId: String,
    val imageUrl: String,
    val content: String?,
    val locationId: String?
)
