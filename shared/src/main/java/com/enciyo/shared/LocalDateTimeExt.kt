package com.enciyo.shared

import kotlinx.datetime.*


val currentSystemTimeZone = TimeZone.currentSystemDefault()

fun today() = Clock.System.now().toLocalDateTime(currentSystemTimeZone)

fun LocalDate.isSameDay(localDateTime: LocalDate) = compareTo(localDateTime) == 0

val LocalDateTime.epochSeconds
    get() = this
        .toInstant(currentSystemTimeZone)
        .epochSeconds


fun LocalTime.copyWithResetSecond() = copy(second = 0)

fun LocalTime.copy(
    hour: Int? = null,
    minute: Int? = null,
    second: Int? = null,
    nanoSeconds: Int? = null,
) = LocalTime(
    hour = hour ?: this.hour,
    minute = minute ?: this.minute,
    second = second ?: this.second,
    nanosecond = nanoSeconds ?: this.nanosecond
)

fun LocalDateTime.copy(
    hour: Int? = null,
    minute: Int? = null,
    second: Int? = null,
    nanoSeconds: Int? = null,
    year: Int? = null,
    month: Month? = null,
    dayOfMonth: Int? = null,
) = LocalDateTime(
    hour = hour ?: this.hour,
    minute = minute ?: this.minute,
    second = second ?: this.second,
    nanosecond = nanoSeconds ?: this.nanosecond,
    year = year ?: this.year,
    month = month ?: this.month,
    dayOfMonth = dayOfMonth ?: this.dayOfMonth
)
