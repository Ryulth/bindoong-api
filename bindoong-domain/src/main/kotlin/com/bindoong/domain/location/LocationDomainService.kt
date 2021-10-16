package com.bindoong.domain.location

import kotlinx.coroutines.flow.Flow
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class LocationDomainService(
    private val locationRepository: LocationRepository
) {
    @Transactional(readOnly = true)
    suspend fun getAll(): Flow<Location> = findAll()

    private suspend fun findAll(): Flow<Location> = locationRepository.findAll()
}
