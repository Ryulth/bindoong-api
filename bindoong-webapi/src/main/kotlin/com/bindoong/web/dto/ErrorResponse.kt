package com.bindoong.web.dto

import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty

@ApiModel
data class ErrorResponse(
    @ApiModelProperty
    val timestamp: String,
    @ApiModelProperty
    val path: String,
    @ApiModelProperty(value = ERROR_DESCRIPTION)
    val code: Int,
    @ApiModelProperty
    val message: String
) {
    companion object {
        const val ERROR_CODE_SERVER_INTERNAL_ERROR: Int = -1000
        const val ERROR_CODE_GENERAL_BAD_REQUEST: Int = 1001
        const val ERROR_CODE_GENERAL_UNAUTHORIZED: Int = 1002
        const val ERROR_CODE_ACCOUNT_NOT_EXIST: Int = 2001
        const val ERROR_CODE_ACCOUNT_DUPLICATED: Int = 2002
        const val ERROR_CODE_ACCOUNT_NOT_ALLOWED: Int = 2003
        const val ERROR_DESCRIPTION = "See https://www.notion.so/yhaejoooo/Error-codes-0257d9d0354c45399394c3c937a354ea"
    }
}
