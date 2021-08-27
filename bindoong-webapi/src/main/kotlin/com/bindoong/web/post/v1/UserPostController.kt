package com.bindoong.web.post.v1

import com.bindoong.domain.CursorRequest
import com.bindoong.service.post.PostService
import com.bindoong.web.dto.CursorPageDto
import com.bindoong.web.dto.PostDto
import com.bindoong.web.dto.toPostDto
import com.bindoong.web.security.UserSessionUtils
import com.fasterxml.jackson.core.type.TypeReference
import io.swagger.annotations.ApiOperation
import mu.KLogging
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController

@RestController
class UserPostController(
    private val postService: PostService
) {
    @ApiOperation(
        nickname = "getMyPosts",
        value = "내 게시물 리스트 페이징",
        response = CursorPageDto::class
    )
    @PreAuthorize("hasRole('BASIC')")
    @GetMapping("/v1/users/me/posts")
    suspend fun getMyPosts(cursorRequest: CursorRequest): CursorPageDto<PostDto> =
        postService.getAll(UserSessionUtils.getCurrentUserId(), cursorRequest).toPostDto()

    @ApiOperation(
        nickname = "getUserPosts",
        value = "유저 게시물 리스트 페이징",
        response = CursorPageDto::class
    )
    @PreAuthorize("hasRole('BASIC')")
    @GetMapping("/v1/users/{userId}/posts")
    suspend fun getUserPosts(
        @PathVariable userId: String,
        cursorRequest: CursorRequest
    ): CursorPageDto<PostDto> = postService.getAll(userId, cursorRequest).toPostDto()

    companion object : KLogging()
}
