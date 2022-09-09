package com.enciyo.data.entity

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation

data class TaskWithPeriods(
    @Embedded
    val task: Task,
    @Relation(
        parentColumn = "taskId",
        entityColumn = "taskCreatorId"
    )
    val periods: List<Period>,
)


