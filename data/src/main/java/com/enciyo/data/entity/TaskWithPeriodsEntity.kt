package com.enciyo.data.entity

import androidx.room.Embedded
import androidx.room.Relation

data class TaskWithPeriodsEntity(
    @Embedded
    val taskEntity: TaskEntity,
    @Relation(
        parentColumn = "taskId",
        entityColumn = "taskCreatorId"
    )
    val periodEntities: List<PeriodEntity>,
)


