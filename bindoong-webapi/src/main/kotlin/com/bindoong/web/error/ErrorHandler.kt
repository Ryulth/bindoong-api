package com.bindoong.web.error

import com.bindoong.core.utils.JsonUtils.asJson
import com.bindoong.service.user.UserAlreadyExistException
import com.bindoong.service.user.UserNotFoundException
import com.bindoong.web.dto.ErrorResponse
import mu.KLogging
import org.springframework.core.annotation.Order
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Component
import org.springframework.web.server.ServerWebExchange
import org.springframework.web.server.WebExceptionHandler
import reactor.core.publisher.Mono
import java.time.LocalDateTime

@Component
@Order(-2)
class ErrorHandler : WebExceptionHandler {
    override fun handle(exchange: ServerWebExchange, exception: Throwable): Mono<Void> {
        val (code, httpStatus) = getCode(exception)
        val errorResponse = ErrorResponse(
            timestamp = LocalDateTime.now().toString(),
            path = exchange.request.path.toString(),
            code = code,
            exception = exception.javaClass.simpleName,
            message = exception.message ?: ""
        )
        exchange.response.statusCode = httpStatus

        val buffer = exchange.response.bufferFactory().wrap(errorResponse.asJson().toByteArray())
        logger.error(exception) { "error response : $errorResponse" }
        return exchange.response.writeWith(Mono.just(buffer))
    }

    private fun getCode(exception: Throwable): Pair<Int, HttpStatus> = when (exception::class) {
        org.springframework.security.access.AccessDeniedException::class ->
            Pair(ErrorResponse.ERROR_CODE_GENERAL_UNAUTHORIZED, HttpStatus.UNAUTHORIZED)
        IllegalArgumentException::class ->
            Pair(ErrorResponse.ERROR_CODE_GENERAL_BAD_REQUEST, HttpStatus.BAD_REQUEST)
        UserNotFoundException::class ->
            Pair(ErrorResponse.ERROR_CODE_ACCOUNT_NOT_EXIST, HttpStatus.BAD_REQUEST)
        UserAlreadyExistException::class ->
            Pair(ErrorResponse.ERROR_CODE_ACCOUNT_DUPLICATED, HttpStatus.BAD_REQUEST)
        else ->
            Pair(ErrorResponse.ERROR_CODE_SERVER_INTERNAL_ERROR, HttpStatus.INTERNAL_SERVER_ERROR)
    }

    companion object : KLogging()
}
