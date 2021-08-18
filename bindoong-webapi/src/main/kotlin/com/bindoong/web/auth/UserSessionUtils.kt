package com.bindoong.web.auth

import com.bindoong.web.security.UserSession
import kotlinx.coroutines.reactive.awaitFirst
import org.springframework.security.core.context.ReactiveSecurityContextHolder

object UserSessionUtils {
    suspend fun getCurrentUserSession(): UserSession =
        ReactiveSecurityContextHolder.getContext().awaitFirst().authentication as UserSession

    suspend fun getCurrentUserId(): Long = getCurrentUserSession().userId
}
