package com.bindoong.domain.location

import kotlinx.coroutines.flow.Flow
import org.springframework.stereotype.Repository

@Repository
interface LocationRepository {
    suspend fun insert(location: Location): Location
    suspend fun update(location: Location): Location
    suspend fun findById(locationId: String): Location?
    suspend fun findAll(): Flow<Location>
    suspend fun existsById(locationId: String): Boolean
}
