package com.bindoong.web.error

import com.bindoong.core.utils.JsonUtils.convertValueAsMap
import com.bindoong.core.utils.JsonUtils.objectMapper
import java.util.Date

data class WebErrorAttribute(
    val timestamp: Date,
    val path: String,
    val status: Int,
    val exception: String,
    val message: String
) {
    fun asMap(): Map<String, Any> = objectMapper.convertValueAsMap(this)
}
