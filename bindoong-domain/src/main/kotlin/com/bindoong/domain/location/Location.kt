package com.bindoong.domain.location

import org.springframework.data.annotation.Id

data class Location(
    @Id
    val locationId: String,
    val displayName: String,
    val enabled: Boolean
)
