package com.project.mobile_university.data.room.converter

import androidx.room.TypeConverter

class GeoTagConverter {
    companion object {
        private const val SEPARATOR = ";"
    }

    @TypeConverter
    fun fromCoordinates(coordinates: List<String>?): String? = coordinates?.joinToString(separator = SEPARATOR)

    @TypeConverter
    fun toCoordinates(coordinates: String?): List<String>? = coordinates?.split(SEPARATOR)
}