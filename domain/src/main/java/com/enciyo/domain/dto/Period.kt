package com.enciyo.domain.dto

import kotlinx.datetime.LocalDateTime

data class Period(
    val time: LocalDateTime,
    val taskId: Int,
)