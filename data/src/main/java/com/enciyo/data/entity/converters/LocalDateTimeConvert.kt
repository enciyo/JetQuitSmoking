package com.enciyo.data.entity.converters

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import kotlinx.datetime.LocalDateTime
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
@ProvidedTypeConverter
class LocalDateTimeConvert @Inject constructor() {
    @TypeConverter
    fun fromLocalDateTime(time: LocalDateTime) = time.toString()

    @TypeConverter
    fun toLocalDateTime(time: String) = LocalDateTime.parse(time)
}


