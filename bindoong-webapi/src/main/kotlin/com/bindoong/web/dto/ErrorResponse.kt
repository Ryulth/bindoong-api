package com.bindoong.web.dto

import com.bindoong.core.utils.JsonUtils.convertValueAsMap
import com.bindoong.core.utils.JsonUtils.objectMapper
import java.util.Date

data class ErrorResponse(
    val timestamp: Date,
    val path: String,
    val status: Int,
    val code: Int,
    val exception: String,
    val message: String
) {
    fun asMap(): Map<String, Any> = objectMapper.convertValueAsMap(this)
    companion object {
        const val ERROR_CODE_SERVER_INTERNAL_ERROR: Int = -1000
        const val ERROR_CODE_GENERAL_BAD_REQUEST: Int = 1001
        const val ERROR_CODE_GENERAL_UNAUTHORIZED: Int = 1002
        const val ERROR_CODE_ACCOUNT_NOT_EXIST: Int = 2001
        const val ERROR_CODE_ACCOUNT_DUPLICATED: Int = 2002
    }
}
