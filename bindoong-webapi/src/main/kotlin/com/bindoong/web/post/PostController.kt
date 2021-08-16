package com.bindoong.web.post

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import mu.KLogging
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping(PostController.BASE_PATH)
class PostController(
    private val postApiService: PostApiService
) {

    @Operation(
        operationId = "createPost",
        summary = "게시물 생성",
    )
    @ApiResponse(responseCode = "200", description = "게시물 생성")
    @PostMapping
    suspend fun createPost(
        @RequestBody request: PostCreateRequest
    ) {
        postApiService.createPost(request)
    }

    companion object : KLogging() {
        const val BASE_PATH = "/v1/post"
    }
}
