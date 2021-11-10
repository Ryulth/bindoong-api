package com.bindoong.domain.notification

import com.bindoong.domain.CursorPage
import com.bindoong.domain.CursorRequest
import org.springframework.stereotype.Repository

@Repository
interface NotificationRepository {
    suspend fun insert(notification: Notification): Notification
    suspend fun update(notification: Notification): Notification
    suspend fun findById(notificationId: String): Notification?
    suspend fun findAllByUserId(userId: String, cursorRequest: CursorRequest): CursorPage<Notification>
}
