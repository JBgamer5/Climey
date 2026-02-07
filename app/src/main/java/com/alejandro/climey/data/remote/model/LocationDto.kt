package com.alejandro.climey.data.remote.model

import com.alejandro.climey.domain.model.Location

data class LocationDto(
    val id: Int? = null,
    val name: String,
    val region: String,
    val country: String
)

fun LocationDto.toLocation(): Location {
    return Location(
        id = id,
        name = name,
        region = region,
        country = country
    )
}
