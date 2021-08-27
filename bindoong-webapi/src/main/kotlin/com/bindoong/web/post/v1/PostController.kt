package com.bindoong.web.post.v1

import com.bindoong.service.post.PostCreateParameter
import com.bindoong.service.post.PostService
import com.bindoong.service.post.PostUpdateParameter
import com.bindoong.web.dto.PostCreateRequest
import com.bindoong.web.dto.PostDto
import com.bindoong.web.dto.PostUpdateRequest
import com.bindoong.web.security.UserSessionUtils
import io.swagger.annotations.ApiOperation
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
    @ApiOperation(
        nickname = "createPost",
        value = "게시물 생성",
        response = PostDto::class
    )
    @PreAuthorize("hasRole('BASIC')")
    @PostMapping("/v1/posts")
    suspend fun createPost(
        @RequestBody request: PostCreateRequest
    ): PostDto = PostDto(postService.create(request.toParameter(UserSessionUtils.getCurrentUserId())))

    @ApiOperation(
        nickname = "getPost",
        value = "게시물 가져오기",
        response = PostDto::class
    )
    @PreAuthorize("hasRole('BASIC')")
    @GetMapping("/v1/posts/{postId}")
    suspend fun getPost(
        @PathVariable postId: String,
    ): PostDto = PostDto(postService.getOrThrow(postId))

    @ApiOperation(
        nickname = "updatePost",
        value = "게시물 수정",
        response = PostDto::class
    )
    @PreAuthorize("hasRole('BASIC')")
    @PutMapping("/v1/posts/{postId}")
    suspend fun updatePost(
        @PathVariable postId: String,
        @RequestBody request: PostUpdateRequest
    ): PostDto = PostDto(postService.update(request.toParameter(UserSessionUtils.getCurrentUserId(), postId)))

    @ApiOperation(
        nickname = "deletePost",
        value = "게시물 삭제",
        response = Nothing::class
    )
    @PreAuthorize("hasRole('BASIC')")
    @DeleteMapping("/v1/posts/{postId}")
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
