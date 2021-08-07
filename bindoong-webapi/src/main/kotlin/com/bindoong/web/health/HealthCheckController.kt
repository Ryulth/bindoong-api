package com.bindoong.web.health

import com.bindoong.web.security.UserSessionUtils
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping(HealthCheckController.BASE_PATH)
class HealthCheckController() {
    @GetMapping
    suspend fun healthCheck() = ResponseEntity.ok().build<Nothing>()

    @PreAuthorize("hasRole('BASIC')")
    @GetMapping("/auth")
    suspend fun healthAuthCheck() = ResponseEntity.ok(UserSessionUtils.getCurrentUserId())

    companion object {
        const val BASE_PATH = "/health"
    }
}
