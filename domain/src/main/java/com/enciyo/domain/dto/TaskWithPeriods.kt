package com.enciyo.domain.dto

data class TaskWithPeriods(
    val task: Task,
    val periods: List<Period>,
)