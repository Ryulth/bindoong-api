package com.bindoong.domain.reaction

import org.springframework.data.annotation.Id

data class Reaction(
    @Id
    val reactionId: String,
    val imageUrl: String,
    val text: String,
    val enabled: Boolean,
    val order: Int
)
