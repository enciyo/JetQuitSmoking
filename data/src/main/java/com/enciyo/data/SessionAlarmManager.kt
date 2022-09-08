package com.enciyo.data

interface SessionAlarmManager {

    operator fun invoke(
        taskId: Int,
        time: String,
        smokeCount: Int,
    )
}






