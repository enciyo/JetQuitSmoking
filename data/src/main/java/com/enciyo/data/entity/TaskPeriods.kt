package com.enciyo.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "tasks_period"
)
data class TaskPeriods(
    @PrimaryKey(autoGenerate = false)
    val taskId: Int,
    val period: List<Period>
)


