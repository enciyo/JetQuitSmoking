package com.enciyo.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.datetime.LocalDateTime

@Entity(
    tableName = "task"
)
data class Task(
    @PrimaryKey(autoGenerate = false)
    val taskId: Int,
    val needSmokeCount: Int,
    val time: LocalDateTime,
)


