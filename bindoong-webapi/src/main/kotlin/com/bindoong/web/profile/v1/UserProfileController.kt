package com.bindoong.web.profile.v1

import com.bindoong.service.profile.UserProfileService
import com.bindoong.web.config.SwaggerConfig
import com.bindoong.web.dto.NicknameRequest
import com.bindoong.web.dto.UserProfileDto
import com.bindoong.web.security.UserSessionUtils
import io.swagger.annotations.ApiOperation
import io.swagger.v3.oas.annotations.tags.Tag
import mu.KLogging
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@Tag(name = SwaggerConfig.ApiTag.PROFILE)
@RestController
class UserProfileController(
    private val userProfileService: UserProfileService
) {
    @ApiOperation(
        nickname = "getMyProfile",
        value = "내 프로필",
        response = UserProfileDto::class,
        tags = [SwaggerConfig.ApiTag.PROFILE]
    )
    @PreAuthorize("hasRole('BASIC')")
    @GetMapping("/v1/users/me")
    suspend fun getMyProfile(): UserProfileDto =
        userProfileService.getUserProfile(UserSessionUtils.getCurrentUserId())
            .let { UserProfileDto(it) }

    @ApiOperation(
        nickname = "getUserProfile",
        value = "유저 프로필",
        response = UserProfileDto::class,
        tags = [SwaggerConfig.ApiTag.PROFILE]
    )
    @PreAuthorize("hasRole('BASIC')")
    @GetMapping("/v1/users/{userId}")
    suspend fun getUserProfile(
        @PathVariable userId: String
    ): UserProfileDto =
        userProfileService.getUserProfile(userId)
            .let { UserProfileDto(it) }

    @ApiOperation(
        nickname = "validateUserNickname",
        value = "유저 닉네임 검증",
        tags = [SwaggerConfig.ApiTag.PROFILE]
    )
    @PostMapping("/v1/users/nickname/validate")
    suspend fun validateUserNickname(
        @RequestBody nicknameRequest: NicknameRequest
    ) {
        userProfileService.validateNickname(nicknameRequest.nickname)
    }

    companion object : KLogging()
}
