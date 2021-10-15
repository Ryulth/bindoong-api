package com.bindoong.web.dto

data class PostCreateRequest(
    val imageUrl: String,
    val content: String?,
    val locationId: String?
)
