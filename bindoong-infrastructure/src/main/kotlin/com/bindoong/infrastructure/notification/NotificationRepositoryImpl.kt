package com.bindoong.infrastructure.notification

import com.bindoong.domain.CursorPage
import com.bindoong.domain.CursorRequest
import com.bindoong.domain.notification.Notification
import com.bindoong.domain.notification.NotificationRepository
import kotlinx.coroutines.flow.lastOrNull
import kotlinx.coroutines.reactive.asFlow
import kotlinx.coroutines.reactor.awaitSingle
import kotlinx.coroutines.reactor.awaitSingleOrNull
import org.springframework.data.domain.Sort
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate
import org.springframework.data.relational.core.query.Criteria
import org.springframework.data.relational.core.query.Query
import org.springframework.stereotype.Component

@Component
class NotificationRepositoryImpl(
    private val template: R2dbcEntityTemplate
) : NotificationRepository {
    override suspend fun insert(notification: Notification): Notification {
        return template.insert(notification).awaitSingle()
    }

    override suspend fun update(notification: Notification): Notification {
        return template.update(notification).awaitSingle()
    }

    override suspend fun findById(notificationId: String): Notification? {
        return template.selectOne(
            Query.query(Criteria.where(COLUMN_NOTIFICATION_ID).`is`(notificationId)),
            Notification::class.java
        ).awaitSingleOrNull()
    }

    override suspend fun findAllByUserId(userId: String, cursorRequest: CursorRequest): CursorPage<Notification> {
        return template.select(
            Query.query(
                Criteria.where(COLUMN_USER_ID).`is`(userId).let {
                    cursorRequest.cursor?.let { cursor ->
                        it.and(Criteria.where(COLUMN_NOTIFICATION_ID).lessThan(cursor))
                    } ?: it
                }
            )
                .sort(Sort.by(Sort.Direction.DESC, COLUMN_NOTIFICATION_ID))
                .limit(cursorRequest.size),
            Notification::class.java
        ).asFlow().let {
            CursorPage(
                content = it,
                CursorPage.Cursor(
                    current = cursorRequest.cursor,
                    next = it.lastOrNull()?.notificationId
                )
            )
        }
    }

    companion object {
        private const val COLUMN_USER_ID = "user_id"
        private const val COLUMN_NOTIFICATION_ID = "notification_id"
    }
}
