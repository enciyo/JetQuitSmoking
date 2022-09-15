package com.enciyo.data.source

import com.enciyo.data.dao.AccountDao
import com.enciyo.data.dao.PeriodDao
import com.enciyo.data.dao.TaskDao
import com.enciyo.data.mapper.toDomain
import com.enciyo.data.mapper.toEntity
import com.enciyo.domain.dto.Account
import com.enciyo.domain.dto.Period
import com.enciyo.domain.dto.Task
import com.enciyo.domain.dto.TaskWithPeriods
import com.enciyo.shared.IoDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class LocalDataSourceImp @Inject constructor(
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
    private val accountDao: AccountDao,
    private val taskDao: TaskDao,
    private val periodDao: PeriodDao,
) : LocalDataSource {

    override suspend fun save(account: Account) =
        onIoThread {
            val accountEntity = account.toEntity()
            val id = accountDao.insert(account.toEntity())
            if (id == accountEntity.userId.toLong()) account
            else throw IllegalStateException("User can't save.")
        }

    override suspend fun isLoggedIn(): Boolean =
        onIoThread { accountDao.getFirstUser() != null }

    override suspend fun save(task: Task): Task = onIoThread {
        val entity = task.toEntity()
        val id = taskDao.insert(entity)
        if (entity.taskId.toLong() == id)
            task
        else throw IllegalStateException("Can't save task ${task.taskId}")

    }

    override suspend fun saveAll(vararg period: Period) = onIoThread {
        periodDao.insert(*period.map { it.toEntity() }.toTypedArray())
    }

    override suspend fun saveAll(vararg task: Task): List<Task> = onIoThread {
        val tasks = task.map { it.toEntity() }.toTypedArray()
        val ids = taskDao.insertAll(*tasks)
        if (ids.contains(-1L).not())
            task.toList()
        else throw IllegalStateException("Can't save tasks")
    }

    override suspend fun account(): Account =
        onIoThread {
            val account = accountDao.getFirstUser() ?: throw IllegalStateException("Not Found User")
            account.toDomain()
        }


    override suspend fun tasks(): List<Task> = onIoThread {
        taskDao.tasks().map { it.toDomain() }
    }

    override suspend fun taskPeriodsById(id: Int): TaskWithPeriods = onIoThread {
        taskDao.getTaskWithPeriods(id)
            .toDomain()
    }

    private suspend fun <T> onIoThread(block: suspend () -> T) =
        withContext(ioDispatcher) { block.invoke() }

}