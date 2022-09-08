package com.enciyo.data.entity.converters

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
@ProvidedTypeConverter
class LocalDateConverter @Inject constructor() {
    @TypeConverter
    fun fromLocalDate(time: LocalDate) = time.toString()

    @TypeConverter
    fun toLocalDate(time: String) = LocalDateTime.parse(time)
}