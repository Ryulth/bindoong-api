package com.bindoong.web.post.v1

import com.bindoong.domain.Cursor
import com.bindoong.domain.Cursorable
import com.bindoong.domain.post.Post
import com.bindoong.service.post.PostService
import com.bindoong.web.dto.CursorDto
import com.bindoong.web.dto.PostDto
import com.bindoong.web.security.UserSessionUtils
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.toList
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
    suspend fun getMyPosts(cursorable: Cursorable): CursorDto<PostDto> =
        postService.getAll(UserSessionUtils.getCurrentUserId(), cursorable).toPostDto()

    @Operation(
        operationId = "getUserPosts",
        summary = "게시물 생성",
    )
    @ApiResponse(responseCode = "200", description = "게시물 생성")
    @PreAuthorize("hasRole('BASIC')")
    @GetMapping("/v1/users/{userId}/posts")
    suspend fun getUserPosts(
        @PathVariable userId: String,
        cursorable: Cursorable
    ): CursorDto<PostDto> = postService.getAll(userId, cursorable).toPostDto()

    private suspend fun Cursor<Post>.toPostDto(): CursorDto<PostDto> = this.let {
        CursorDto(
            it.content.map { post -> PostDto(post) }.toList(),
            it.current,
            it.next
        )
    }

    companion object : KLogging()
}
