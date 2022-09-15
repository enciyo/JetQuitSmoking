package com.enciyo.domain.dto

import kotlinx.datetime.LocalDateTime

data class Task(
    val taskId: Int,
    val needSmokeCount: Int,
    val taskTime: LocalDateTime,
)


