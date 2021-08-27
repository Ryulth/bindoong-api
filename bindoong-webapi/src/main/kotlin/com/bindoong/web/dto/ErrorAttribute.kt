package com.bindoong.web.dto

import com.bindoong.core.utils.JsonUtils.convertValueAsMap
import com.bindoong.core.utils.JsonUtils.objectMapper
import java.util.Date

data class ErrorAttribute(
    val timestamp: Date,
    val path: String,
    val status: Int,
    val exception: String,
    val message: String
) {
    fun asMap(): Map<String, Any> = objectMapper.convertValueAsMap(this)
}
