package com.bindoong.web.error

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
            WebErrorAttribute(
                timestamp = Date(),
                path = request.path(),
                status = getStatusCode(exception),
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
        else -> HttpStatus.INTERNAL_SERVER_ERROR.value()
    }

    companion object : KLogging()
}