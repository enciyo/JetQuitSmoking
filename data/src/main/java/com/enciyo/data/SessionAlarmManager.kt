package com.enciyo.data

import kotlinx.datetime.LocalDateTime

interface SessionAlarmManager {

    operator fun invoke(
        taskId: Int,
        time: LocalDateTime,
        smokeCount: Int,
    )
}






