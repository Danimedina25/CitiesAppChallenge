package com.danifitdev.citiesappchallenge.cities.data.local.converters

import androidx.room.TypeConverter
import com.danifitdev.citiesappchallenge.cities.domain.model.CoordModel
import org.json.JSONObject

class Converters {
    @TypeConverter
    fun fromCoordModel(coord: CoordModel): String {
        val json = JSONObject()
        json.put("lat", coord.lat)
        json.put("lon", coord.lon)
        return json.toString()
    }

    @TypeConverter
    fun toCoordModel(coordString: String): CoordModel {
        val json = JSONObject(coordString)
        val lat = json.getDouble("lat")
        val lon = json.getDouble("lon")
        return CoordModel(lat, lon)
    }
}