package com.bindoong.web.post.v1

import com.bindoong.service.post.PostService
import com.bindoong.web.dto.PostDto
import com.bindoong.web.security.UserSessionUtils
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import mu.KLogging
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController

@RestController
class UserPostController(
    private val postService: PostService
) {
    @Operation(
        operationId = "getMyPosts",
        summary = "게시물 생성",
    )
    @ApiResponse(responseCode = "200", description = "게시물 생성")
    @PreAuthorize("hasRole('BASIC')")
    @GetMapping("/v1/users/me/posts")
    suspend fun getMyPosts(): Flow<PostDto> = postService.getAll(UserSessionUtils.getCurrentUserId()).map { PostDto(it) }

    @Operation(
        operationId = "getUserPosts",
        summary = "게시물 생성",
    )
    @ApiResponse(responseCode = "200", description = "게시물 생성")
    @PreAuthorize("hasRole('BASIC')")
    @GetMapping("/v1/users/{userId}/posts")
    suspend fun getUserPosts(
        @PathVariable userId: String
    ): Flow<PostDto> = postService.getAll(userId).map { PostDto(it) }

    companion object : KLogging()
}
