package com.bindoong.web.post.v1

import com.bindoong.domain.post.Post
import com.bindoong.service.post.PostCreateParameter
import com.bindoong.service.post.PostService
import com.bindoong.service.post.PostUpdateParameter
import com.bindoong.web.security.UserSessionUtils
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import mu.KLogging
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping(PostController.BASE_PATH)
class PostController(
    private val postService: PostService
) {
    @Operation(
        operationId = "createPost",
        summary = "게시물 생성",
    )
    @ApiResponse(responseCode = "200", description = "게시물 생성")
    @PreAuthorize("hasRole('BASIC')")
    @PostMapping
    suspend fun createPost(
        @RequestBody request: PostCreateRequest
    ): PostResponse = PostResponse(postService.create(request.toParameter(UserSessionUtils.getCurrentUserId())))

    @Operation(
        operationId = "updatePost",
        summary = "게시물 수정",
    )
    @ApiResponse(responseCode = "200", description = "게시물 수정")
    @PreAuthorize("hasRole('BASIC')")
    @PutMapping("/{postId}")
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

    companion object : KLogging() {
        const val BASE_PATH = "/v1/posts"
    }
}

data class PostCreateRequest(
    val imageUrl: String
)

data class PostUpdateRequest(
    val imageUrl: String
)

data class PostResponse(
    val postId: String,
    val imageUrl: String
) {
    companion object {
        @JvmStatic
        operator fun invoke(post: Post) = PostResponse(
            postId = post.postId,
            imageUrl = post.imageUrl
        )
    }
}
