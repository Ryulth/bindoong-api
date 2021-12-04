package com.bindoong.domain.reaction

import kotlinx.coroutines.flow.Flow
import org.springframework.stereotype.Repository

@Repository
interface ReactionRepository {
    suspend fun findAll(): Flow<Reaction>
}
