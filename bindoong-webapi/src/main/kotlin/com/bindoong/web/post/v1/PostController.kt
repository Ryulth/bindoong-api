package com.bindoong.web.post.v1

import com.bindoong.service.post.PostCreateParameter
import com.bindoong.service.post.PostService
import com.bindoong.service.post.PostUpdateParameter
import com.bindoong.web.dto.PostCreateRequest
import com.bindoong.web.dto.PostResponse
import com.bindoong.web.dto.PostUpdateRequest
import com.bindoong.web.security.UserSessionUtils
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import mu.KLogging
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class PostController(
    private val postService: PostService
) {
    @Operation(
        operationId = "createPost",
        summary = "게시물 생성",
    )
    @ApiResponse(responseCode = "200", description = "게시물 생성")
    @PreAuthorize("hasRole('BASIC')")
    @PostMapping("/v1/posts")
    suspend fun createPost(
        @RequestBody request: PostCreateRequest
    ): PostResponse = PostResponse(postService.create(request.toParameter(UserSessionUtils.getCurrentUserId())))

    @Operation(
        operationId = "getPost",
        summary = "게시물 가져오기",
    )
    @ApiResponse(responseCode = "200", description = "게시물 가져오기")
    @PreAuthorize("hasRole('BASIC')")
    @GetMapping("/v1/posts/{postId}")
    suspend fun getPost(
        @PathVariable postId: String,
    ): PostResponse = PostResponse(postService.getOrThrow(postId))

    @Operation(
        operationId = "updatePost",
        summary = "게시물 수정",
    )
    @ApiResponse(responseCode = "200", description = "게시물 수정")
    @PreAuthorize("hasRole('BASIC')")
    @PutMapping("/v1/posts/{postId}")
    suspend fun updatePost(
        @PathVariable postId: String,
        @RequestBody request: PostUpdateRequest
    ): PostResponse = PostResponse(postService.update(request.toParameter(UserSessionUtils.getCurrentUserId(), postId)))

    @Operation(
        operationId = "deletePost",
        summary = "게시물 삭제",
    )
    @ApiResponse(responseCode = "200", description = "게시물 삭제")
    @PreAuthorize("hasRole('BASIC')")
    @DeleteMapping("/{postId}")
    suspend fun deletePost(
        @PathVariable postId: String,
    ) {
        postService.delete(postId)
    }

    private fun PostCreateRequest.toParameter(userId: String) = PostCreateParameter(
        userId = userId,
        imageUrl = imageUrl
    )

    private fun PostUpdateRequest.toParameter(userId: String, postId: String) = PostUpdateParameter(
        userId = userId,
        postId = postId,
        imageUrl = imageUrl
    )

    companion object : KLogging()
}
