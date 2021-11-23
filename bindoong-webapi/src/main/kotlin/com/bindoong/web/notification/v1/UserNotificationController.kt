package com.bindoong.web.notification.v1

import com.bindoong.domain.CursorRequest
import com.bindoong.service.notification.NotificationService
import com.bindoong.web.config.SwaggerConfig
import com.bindoong.web.dto.CursorPageDto
import com.bindoong.web.dto.LocationDto
import com.bindoong.web.dto.NotificationDto
import io.swagger.annotations.ApiOperation
import io.swagger.v3.oas.annotations.tags.Tag
import mu.KLogging
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@Tag(name = SwaggerConfig.ApiTag.NOTIFICATION)
@RestController
class UserNotificationController(
    private val notificationService: NotificationService
) {
    @ApiOperation(
        nickname = "getLocations",
        value = "위치 정보들",
        response = Array<LocationDto>::class,
        tags = [SwaggerConfig.ApiTag.LOCATION]
    )
    @PreAuthorize("hasRole('BASIC')")
    @GetMapping("/v1/users/me/notifications")
    suspend fun getNotifications(
        cursorRequest: CursorRequest
    ): CursorPageDto<NotificationDto> = CursorPageDto(
        emptyList(),
        CursorPageDto.CursorDto(
            null,
            null
        )
    )

    companion object : KLogging()
}
