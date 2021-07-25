package com.bindoong.web.security

import kotlinx.coroutines.reactive.awaitFirst
import org.springframework.security.core.context.ReactiveSecurityContextHolder

object UserSessionUtils {
    suspend fun getCurrentUserSession(): UserSession =
        ReactiveSecurityContextHolder.getContext().awaitFirst().authentication as UserSession

    suspend fun getCurrentUserId(): Long = getCurrentUserSession().userId
}
