package com.bindoong.service.location

import com.bindoong.domain.location.Location
import com.bindoong.domain.location.LocationDomainService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filter
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class LocationService(
    private val locationDomainService: LocationDomainService
) {
    @Transactional(readOnly = true)
    suspend fun getAllEnabled(): Flow<Location> = locationDomainService.getAll().filter { it.enabled }
}
