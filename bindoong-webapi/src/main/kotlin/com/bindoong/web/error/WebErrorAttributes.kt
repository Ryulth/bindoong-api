package com.bindoong.web.error

import com.bindoong.service.user.UserNotFoundException
import com.bindoong.web.dto.ErrorResponse
import mu.KLogging
import org.springframework.boot.web.error.ErrorAttributeOptions
import org.springframework.boot.web.reactive.error.DefaultErrorAttributes
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest
import java.util.Date

@Component
class WebErrorAttributes : DefaultErrorAttributes() {
    override fun getErrorAttributes(request: ServerRequest, options: ErrorAttributeOptions): Map<String, Any> =
        getException(request).let { exception ->

            ErrorResponse(
                timestamp = Date(),
                path = request.path(),
                status = getStatusCode(exception),
                code = getCode(exception),
                exception = exception.javaClass.simpleName,
                message = exception.message ?: ""
            ).asMap()
        }

    private fun getException(request: ServerRequest): Throwable = super.getError(request)
        .also {
            logger.error(it) { "error path : ${request.path()}" }
        }

    private fun getStatusCode(exception: Throwable): Int = when (exception::class) {
        org.springframework.security.access.AccessDeniedException::class ->
            HttpStatus.UNAUTHORIZED.value()
        UserNotFoundException::class ->
            HttpStatus.BAD_REQUEST.value()
        else -> HttpStatus.INTERNAL_SERVER_ERROR.value()
    }

    private fun getCode(exception: Throwable): Int = when (exception::class) {
        org.springframework.security.access.AccessDeniedException::class ->
            ErrorResponse.ERROR_CODE_GENERAL_UNAUTHORIZED
        UserNotFoundException::class ->
            ErrorResponse.ERROR_CODE_ACCOUNT_NOT_EXIST
        else -> ErrorResponse.ERROR_CODE_SERVER_INTERNAL_ERROR
    }

    companion object : KLogging()
}
