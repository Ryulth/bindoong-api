package com.bindoong.web.profile.v1

import com.bindoong.service.profile.UserProfileService
import com.bindoong.web.dto.UserProfileDto
import com.bindoong.web.security.UserSessionUtils
import io.swagger.annotations.ApiOperation
import mu.KLogging
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController

@RestController
class UserProfileController(
    private val userProfileService: UserProfileService
) {
    @ApiOperation(
        nickname = "getMyProfile",
        value = "내 프로필",
        response = UserProfileDto::class
    )
    @PreAuthorize("hasRole('BASIC')")
    @GetMapping("/v1/users/me")
    suspend fun getMyProfile(): UserProfileDto =
        userProfileService.getUserProfile(UserSessionUtils.getCurrentUserId())
            .let { UserProfileDto(it) }

    @ApiOperation(
        nickname = "getUserProfile",
        value = "유저 프로필",
        response = UserProfileDto::class
    )
    @PreAuthorize("hasRole('BASIC')")
    @GetMapping("/v1/users/{userId}")
    suspend fun getUserProfile(
        @PathVariable userId: String
    ): UserProfileDto =
        userProfileService.getUserProfile(userId)
            .let { UserProfileDto(it) }

    companion object : KLogging()
}
