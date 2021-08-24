package com.bindoong.web.post.v1

import com.bindoong.domain.Cursorable
import com.bindoong.service.post.PostService
import com.bindoong.web.dto.CursorDto
import com.bindoong.web.dto.PostDto
import com.bindoong.web.dto.toPostDto
import com.bindoong.web.security.UserSessionUtils
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
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
        summary = "내 게시물 리스트 페이징",
    )
    @ApiResponse(responseCode = "200", description = "내 게시물 리스트 페이징")
    @PreAuthorize("hasRole('BASIC')")
    @GetMapping("/v1/users/me/posts")
    suspend fun getMyPosts(cursorable: Cursorable): CursorDto<PostDto> =
        postService.getAll(UserSessionUtils.getCurrentUserId(), cursorable).toPostDto()

    @Operation(
        operationId = "getUserPosts",
        summary = "유저 게시물 리스트 페이징",
    )
    @ApiResponse(responseCode = "200", description = "유저 게시물 리스트 페이징")
    @PreAuthorize("hasRole('BASIC')")
    @GetMapping("/v1/users/{userId}/posts")
    suspend fun getUserPosts(
        @PathVariable userId: String,
        cursorable: Cursorable
    ): CursorDto<PostDto> = postService.getAll(userId, cursorable).toPostDto()

    companion object : KLogging()
}
