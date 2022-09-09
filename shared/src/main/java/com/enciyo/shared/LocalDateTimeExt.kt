package com.enciyo.shared

import kotlinx.datetime.*


val currentSystemTimeZone = TimeZone.currentSystemDefault()

fun today() = Clock.System.now().toLocalDateTime(currentSystemTimeZone)

fun LocalDate.isSameDay(localDateTime: LocalDate) = compareTo(localDateTime) == 0

val LocalDateTime.epochSeconds
    get() = this
        .toInstant(currentSystemTimeZone)
        .epochSeconds



