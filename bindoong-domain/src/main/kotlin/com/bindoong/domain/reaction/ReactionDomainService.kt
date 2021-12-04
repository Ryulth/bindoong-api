package com.bindoong.domain.reaction

import kotlinx.coroutines.flow.Flow
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ReactionDomainService(
    private val reactionRepository: ReactionRepository
) {
    @Transactional(readOnly = true)
    suspend fun getAll(): Flow<Reaction> = reactionRepository.findAll()
}
