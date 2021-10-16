package com.bindoong.infrastructure.location

import com.bindoong.domain.location.Location
import com.bindoong.domain.location.LocationRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.reactive.asFlow
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate
import org.springframework.data.r2dbc.core.select
import org.springframework.stereotype.Component

@Component
class LocationRepositoryImpl(
    private val template: R2dbcEntityTemplate
) : LocationRepository {
    override suspend fun findAll(): Flow<Location> = template.select<Location>().all().asFlow()
}
