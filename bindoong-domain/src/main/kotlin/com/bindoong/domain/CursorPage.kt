package com.bindoong.domain

import kotlinx.coroutines.flow.Flow

data class CursorPage<T>(
    val content: Flow<T>,
    val cursor: Cursor
) {
    data class Cursor(
        val current: String? = null,
        val next: String? = null
    )
}
