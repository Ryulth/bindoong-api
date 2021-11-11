package com.bindoong.web.post.v1

import com.bindoong.service.post.CommentCreateParameter
import com.bindoong.service.post.CommentService
import com.bindoong.service.post.CommentUpdateParameter
import com.bindoong.web.config.SwaggerConfig
import com.bindoong.web.dto.CommentCreateRequest
import com.bindoong.web.dto.CommentDto
import com.bindoong.web.dto.CommentUpdateRequest
import com.bindoong.web.security.UserSessionUtils
import io.swagger.annotations.ApiOperation
import io.swagger.v3.oas.annotations.tags.Tag
import mu.KLogging
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@Tag(name = SwaggerConfig.ApiTag.POST)
@RestController
class CommentController(
    private val commentService: CommentService
) {
    @ApiOperation(
        nickname = "createComment",
        value = "게시물 생성",
        response = CommentDto::class,
        tags = [SwaggerConfig.ApiTag.POST]
    )
    @PreAuthorize("hasRole('BASIC')")
    @PostMapping("/v1/posts/{postId}/comments")
    suspend fun createComment(
        @PathVariable postId: String,
        @RequestBody request: CommentCreateRequest
    ): CommentDto = CommentDto(commentService.create(request.toParameter(UserSessionUtils.getCurrentUserId(), postId)))

    @ApiOperation(
        nickname = "getComment",
        value = "게시물 가져오기",
        response = CommentDto::class,
        tags = [SwaggerConfig.ApiTag.POST]
    )
    @PreAuthorize("hasRole('BASIC')")
    @GetMapping("/v1/posts/{postId}/comments/{commentId}")
    suspend fun getComment(
        @PathVariable postId: String,
        @PathVariable commentId: String,
    ): CommentDto = CommentDto(commentService.getOrThrow(commentId))

    @ApiOperation(
        nickname = "updateComment",
        value = "게시물 수정",
        response = CommentDto::class,
        tags = [SwaggerConfig.ApiTag.POST]
    )
    @PreAuthorize("hasRole('BASIC')")
    @PutMapping("/v1/posts/{postId}/comments/{commentId}")
    suspend fun updateComment(
        @PathVariable postId: String,
        @PathVariable commentId: String,
        @RequestBody request: CommentUpdateRequest
    ): CommentDto =
        CommentDto(commentService.update(request.toParameter(UserSessionUtils.getCurrentUserId(), postId, commentId)))

    @ApiOperation(
        nickname = "deleteComment",
        value = "게시물 삭제",
        response = Nothing::class,
        tags = [SwaggerConfig.ApiTag.POST]
    )
    @PreAuthorize("hasRole('BASIC')")
    @DeleteMapping("/v1/posts/{postId}/comments/{commentId}")
    suspend fun deleteComment(
        @PathVariable postId: String,
        @PathVariable commentId: String,
    ) {
        commentService.delete(commentId)
    }

    private fun CommentCreateRequest.toParameter(userId: String, postId: String) = CommentCreateParameter(
        userId = userId,
        postId = postId,
        content = this.content
    )

    private fun CommentUpdateRequest.toParameter(userId: String, postId: String, commentId: String) =
        CommentUpdateParameter(
            userId = userId,
            commentId = commentId,
            postId = postId,
            content = this.content
        )

    companion object : KLogging()
}
