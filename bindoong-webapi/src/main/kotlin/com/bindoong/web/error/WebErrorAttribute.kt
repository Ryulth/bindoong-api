package com.bindoong.web.error

import com.bindoong.core.utils.JsonUtils.objectMapper
import com.fasterxml.jackson.core.type.TypeReference
import java.util.Date

data class WebErrorAttribute(
    val timestamp: Date,
    val path: String,
    val status: Int,
    val exception: String,
    val message: String
) {
    fun asMap() = objectMapper.convertValue(this, object: TypeReference<Map<String, Any>>() {})
}