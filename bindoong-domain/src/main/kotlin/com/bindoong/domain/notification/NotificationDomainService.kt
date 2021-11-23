package com.bindoong.domain.notification

import com.bindoong.domain.CursorPage
import com.bindoong.domain.CursorRequest
import com.github.f4b6a3.ulid.UlidCreator
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class NotificationDomainService(
    private val notificationRepository: NotificationRepository
) {
    @Transactional
    suspend fun create(parameter: CreateParameter): Notification =
        Notification(
            notificationId = UlidCreator.getUlid().toString(),
            senderUserId = parameter.senderUserId,
            receiverUserId = parameter.receiverUserId,
            notificationType = parameter.notificationType,
            ack = false
        ).let {
            notificationRepository.insert(it)
        }

    @Transactional
    suspend fun update(parameter: UpdateParameter): Notification =
        Notification(
            notificationId = parameter.notificationId,
            senderUserId = parameter.senderUserId,
            receiverUserId = parameter.receiverUserId,
            notificationType = parameter.notificationType,
            ack = parameter.ack
        ).let {
            notificationRepository.update(it)
        }

    @Transactional(readOnly = true)
    suspend fun getByNotificationId(notificationId: String): Notification? = notificationRepository.findById(notificationId)

    @Transactional(readOnly = true)
    suspend fun getAllByUserId(userId: String, cursorRequest: CursorRequest): CursorPage<Notification> =
        notificationRepository.findAllByUserId(userId, cursorRequest)
}

data class CreateParameter(
    val senderUserId: String,
    val receiverUserId: String,
    val notificationType: NotificationType,
)

data class UpdateParameter(
    val notificationId: String,
    val senderUserId: String,
    val receiverUserId: String,
    val notificationType: NotificationType,
    val ack: Boolean
)
