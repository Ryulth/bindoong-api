package com.bindoong.domain.location

import kotlinx.coroutines.flow.Flow
import org.springframework.stereotype.Repository

@Repository
interface LocationRepository {
    suspend fun findAll(): Flow<Location>
}
