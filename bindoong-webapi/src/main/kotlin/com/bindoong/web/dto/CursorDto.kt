package com.bindoong.web.dto

data class CursorDto<T>(
    val content: List<T>,
    val current: String?,
    val next: String?
)
