package com.danifitdev.citiesappchallenge.cities.data.mappers

import com.danifitdev.citiesappchallenge.cities.data.dto.CityDto
import com.danifitdev.citiesappchallenge.cities.data.dto.CoordDto
import com.danifitdev.citiesappchallenge.cities.data.local.entities.CityEntity
import com.danifitdev.citiesappchallenge.cities.domain.model.CityModel
import com.danifitdev.citiesappchallenge.cities.domain.model.CoordModel


fun CityDto.toDomain(): CityModel {
    return CityModel(
        _id = _id,
        coord = coord.toDomain(),
        country = country,
        name = name,
        isFavorite = false
    )
}

fun CoordDto.toDomain(): CoordModel {
    return CoordModel(
        lat = lat,
        lon = lon
    )
}


fun CityEntity.toDomain(): CityModel {
    return CityModel(
        _id = _id!!,
        coord = coord,
        country = country!!,
        name = name!!,
        isFavorite = isFavorite
    )
}

fun CityModel.toEntity(): CityEntity {
    return CityEntity(
        _id = _id,
        coord = coord,
        country = country,
        name = name,
        isFavorite = isFavorite
    )
}


