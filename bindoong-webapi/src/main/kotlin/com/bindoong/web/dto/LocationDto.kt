package com.bindoong.web.dto

import com.bindoong.domain.location.Location

data class LocationDto(
    val locationId: String,
    val displayName: String
) {
    companion object {
        @JvmStatic
        operator fun invoke(location: Location) = LocationDto(
            locationId = location.locationId,
            displayName = location.displayName
        )
    }
}
