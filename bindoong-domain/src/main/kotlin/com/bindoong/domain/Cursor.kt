package com.bindoong.domain

import kotlinx.coroutines.flow.Flow

data class Cursor<T>(
    val content: Flow<T>,
    val currentCursor: String? = null,
    val nextCursor: String? = null
)
