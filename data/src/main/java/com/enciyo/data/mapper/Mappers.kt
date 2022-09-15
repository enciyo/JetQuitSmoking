package com.enciyo.data.mapper

import com.enciyo.data.entity.AccountEntity
import com.enciyo.data.entity.PeriodEntity
import com.enciyo.data.entity.TaskEntity
import com.enciyo.data.entity.TaskWithPeriodsEntity
import com.enciyo.domain.dto.Account
import com.enciyo.domain.dto.Period
import com.enciyo.domain.dto.Task
import com.enciyo.domain.dto.TaskWithPeriods


fun AccountEntity.toDomain() = Account(
    name = name,
    cigarettesSmokedPerDay = cigarettesSmokedPerDay,
    cigarettesInAPack = cigarettesInAPack,
    pricePerPack = pricePerPack
)

fun Account.toEntity() = AccountEntity(
    name = name,
    cigarettesSmokedPerDay = cigarettesSmokedPerDay,
    cigarettesInAPack = cigarettesInAPack,
    pricePerPack = pricePerPack
)

fun TaskWithPeriodsEntity.toDomain() = TaskWithPeriods(
    task = taskEntity.toDomain(),
    periods = periodEntities.map(PeriodEntity::toDomain)
)

fun TaskEntity.toDomain() = Task(
    taskId = taskId,
    needSmokeCount = needSmokeCount,
    taskTime = time
)

fun Task.toEntity() = TaskEntity(
    taskId = taskId,
    needSmokeCount = needSmokeCount,
    time = taskTime
)

fun PeriodEntity.toDomain() = Period(
    time = time,
    taskId = taskCreatorId
)

fun Period.toEntity() = PeriodEntity(
    taskCreatorId = taskId,
    time = time
)