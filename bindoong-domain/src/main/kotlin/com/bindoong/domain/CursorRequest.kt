package com.bindoong.domain

data class CursorRequest(
    val cursor: String?,
    val size: Int = 10
)
