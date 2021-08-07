package com.bindoong.core.utils

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.afterburner.AfterburnerModule
import com.fasterxml.jackson.module.kotlin.registerKotlinModule

object JsonUtils {
    val objectMapper: ObjectMapper = ObjectMapper().registerKotlinModule()
        .registerModule(JavaTimeModule())
        .registerModule(AfterburnerModule())
        .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)

    fun ObjectMapper.convertValueAsMap(value: Any): Map<String, Any> =
        objectMapper.convertValue(value, object : TypeReference<Map<String, Any>>() {})
}
