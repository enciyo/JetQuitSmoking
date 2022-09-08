package com.enciyo.shared

import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toInstant


val LocalDateTime.epochSeconds
    get() = this
        .toInstant(TimeZone.currentSystemDefault())
        .epochSeconds
