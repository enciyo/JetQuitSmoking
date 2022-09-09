package com.enciyo.data.dao.converters

import com.squareup.moshi.FromJson
import com.squareup.moshi.ToJson
import kotlinx.datetime.LocalDateTime
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocalDateTimeJsonAdapter @Inject constructor() {
    @ToJson
    fun toJson(@DateTime date: LocalDateTime) = date.toString()

    @FromJson
    @DateTime
    fun fromJson(date: String) = LocalDateTime.parse(date)
}