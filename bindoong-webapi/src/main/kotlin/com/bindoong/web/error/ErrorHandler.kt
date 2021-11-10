package com.bindoong.web.error

import com.bindoong.core.exceptions.NicknameDuplicatedException
import com.bindoong.core.exceptions.UserAlreadyExistException
import com.bindoong.core.exceptions.UserNotAllowedException
import com.bindoong.core.exceptions.UserNotFoundException
import com.bindoong.core.utils.JsonUtils.asJson
import com.bindoong.web.dto.ErrorResponse
import mu.KLogging
import org.springframework.core.annotation.Order
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClientResponseException
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
            message = exception.message ?: ""
        )
        exchange.response.statusCode = httpStatus
        val buffer = exchange.response.bufferFactory().wrap(errorResponse.asJson().toByteArray())
        logger.error(exception) { "error response : $errorResponse" }
        return exchange.response.writeWith(Mono.just(buffer))
    }

    private fun getCode(exception: Throwable): Pair<Int, HttpStatus> = when (exception) {
        is org.springframework.security.access.AccessDeniedException ->
            Pair(ErrorResponse.ERROR_CODE_GENERAL_UNAUTHORIZED, HttpStatus.UNAUTHORIZED)
        is IllegalArgumentException ->
            Pair(ErrorResponse.ERROR_CODE_GENERAL_BAD_REQUEST, HttpStatus.BAD_REQUEST)
        is UserNotFoundException ->
            Pair(ErrorResponse.ERROR_CODE_ACCOUNT_NOT_EXIST, HttpStatus.BAD_REQUEST)
        is UserAlreadyExistException ->
            Pair(ErrorResponse.ERROR_CODE_ACCOUNT_DUPLICATED, HttpStatus.BAD_REQUEST)
        is UserNotAllowedException ->
            Pair(ErrorResponse.ERROR_CODE_ACCOUNT_NOT_ALLOWED, HttpStatus.FORBIDDEN)
        is NicknameDuplicatedException ->
            Pair(ErrorResponse.ERROR_CODE_USER_NICKNAME_DUPLICATED, HttpStatus.BAD_REQUEST)
        is WebClientResponseException ->
            when (exception.statusCode) {
                HttpStatus.UNAUTHORIZED ->
                    Pair(ErrorResponse.ERROR_CODE_GENERAL_UNAUTHORIZED, HttpStatus.UNAUTHORIZED)
                HttpStatus.BAD_REQUEST ->
                    Pair(ErrorResponse.ERROR_CODE_GENERAL_BAD_REQUEST, HttpStatus.BAD_REQUEST)
                HttpStatus.FORBIDDEN ->
                    Pair(ErrorResponse.ERROR_CODE_ACCOUNT_NOT_ALLOWED, HttpStatus.FORBIDDEN)
                else ->
                    Pair(ErrorResponse.ERROR_CODE_SERVER_INTERNAL_ERROR, HttpStatus.INTERNAL_SERVER_ERROR)
            }
        else ->
            Pair(ErrorResponse.ERROR_CODE_SERVER_INTERNAL_ERROR, HttpStatus.INTERNAL_SERVER_ERROR)
    }

    companion object : KLogging()
}
