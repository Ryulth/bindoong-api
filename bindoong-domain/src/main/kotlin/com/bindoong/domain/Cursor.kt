package com.bindoong.domain

import kotlinx.coroutines.flow.Flow

data class Cursor<T>(
    val content: Flow<T>,
    val current: String? = null,
    val next: String? = null
)
