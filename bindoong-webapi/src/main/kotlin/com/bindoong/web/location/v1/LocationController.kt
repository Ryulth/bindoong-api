package com.bindoong.web.location.v1

import com.bindoong.service.location.LocationService
import com.bindoong.web.config.SwaggerConfig
import com.bindoong.web.dto.LocationDto
import io.swagger.annotations.ApiOperation
import io.swagger.v3.oas.annotations.tags.Tag
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import mu.KLogging
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@Tag(name = SwaggerConfig.ApiTag.LOCATION)
@RestController
class LocationController(
    private val locationService: LocationService
) {
    @ApiOperation(
        nickname = "getLocations",
        value = "위치 정보들",
        response = Array<LocationDto>::class,
        tags = [SwaggerConfig.ApiTag.LOCATION]
    )
    @PreAuthorize("hasRole('BASIC')")
    @GetMapping("/v1/locations")
    suspend fun getLocations(): Flow<LocationDto> = locationService.getAllEnabled().map { LocationDto(it) }

    companion object : KLogging()
}
