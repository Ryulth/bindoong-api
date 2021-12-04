package com.bindoong.domain.reaction

import org.springframework.data.annotation.Id

data class PostReaction(
    @Id
    val postReactionId: String,
    val userId: String,
    val reactionId: String,
    val count: Int
)
