package com.enciyo.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.enciyo.data.dao.converters.DateTime
import kotlinx.datetime.LocalDateTime

@Entity(
    tableName = "period"
)
data class PeriodEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val taskCreatorId: Int,
    @DateTime val time: LocalDateTime,
)