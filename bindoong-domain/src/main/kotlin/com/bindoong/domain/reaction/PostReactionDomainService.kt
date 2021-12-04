package com.bindoong.domain.reaction

import org.springframework.stereotype.Service

@Service
class PostReactionDomainService(
    private val postReactionRepository: PostReactionRepository
)
