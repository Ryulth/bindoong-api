package com.bindoong.infrastructure.reaction

import com.bindoong.domain.reaction.Reaction
import com.bindoong.domain.reaction.ReactionRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.reactive.asFlow
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate
import org.springframework.data.r2dbc.core.select
import org.springframework.stereotype.Component

@Component
class ReactionRepositoryImpl(
    private val template: R2dbcEntityTemplate
) : ReactionRepository {
    override suspend fun findAll(): Flow<Reaction> = template.select<Reaction>().all().asFlow()
}
