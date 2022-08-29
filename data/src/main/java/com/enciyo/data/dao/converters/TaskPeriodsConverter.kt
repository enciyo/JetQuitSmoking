package com.enciyo.data.dao.converters

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.enciyo.data.entity.Period
import com.squareup.moshi.Moshi
import com.squareup.moshi.adapter
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory

@OptIn(ExperimentalStdlibApi::class)
@ProvidedTypeConverter
object TaskPeriodsConverter {

    private val moshi by lazy {
        Moshi.Builder()
            .addLast(KotlinJsonAdapterFactory())
            .build()
    }

    @TypeConverter
    fun from(data: String?): List<Period> {
        if (data == null) {
            return emptyList()
        }
        return moshi.adapter<List<Period>>()
            .fromJson(data)
            .orEmpty()
    }

    @TypeConverter
    fun to(data: List<Period>): String {
        return moshi.adapter<List<Period>>()
            .toJson(data)
            .orEmpty()
    }

}