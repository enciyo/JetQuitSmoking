package com.enciyo.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "task"
)
data class Task(
    @PrimaryKey(autoGenerate = false)
    val taskId: Int,
    val needSmokeCount: Int
)


