package com.bindoong.web.auth.v1

import com.bindoong.web.dto.TokenResponse
import com.bindoong.web.security.TokenProvider
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.media.ExampleObject
import io.swagger.v3.oas.annotations.responses.ApiResponse
import org.springframework.context.annotation.Profile
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@Profile("!prod")
@RequestMapping("/v1/token")
class TokenController(
    private val tokenProvider: TokenProvider
) {
    @Operation(
        operationId = "createTokenForTest",
        summary = "토큰 발급",
    )
    @ApiResponse(responseCode = "200", description = "토큰 값")
    @PostMapping
    fun createTokenForTest(
        @Parameter(
            name = "subject",
            description = "토큰 발급을 위한 userId 유효 시간은 60분입니다.",
            required = true,
            examples = [
                ExampleObject(name = "userId", value = "123123")
            ]
        )
        @RequestBody request: AccessTokenRequest
    ): TokenResponse =
        TokenResponse(
            tokenProvider.createToken(request.userId)
        )

    data class AccessTokenRequest(
        val userId: String
    )
}
