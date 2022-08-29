package com.enciyo.data.source

import com.enciyo.data.dao.AccountDao
import com.enciyo.data.dao.TaskDao
import com.enciyo.data.dao.TaskPeriodsDao
import com.enciyo.data.entity.Account
import com.enciyo.data.entity.Task
import com.enciyo.data.entity.TaskPeriods
import com.enciyo.shared.IoDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import java.lang.IllegalStateException
import javax.inject.Inject

class LocalDataSourceImp @Inject constructor(
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
    private val accountDao: AccountDao,
    private val taskDao: TaskDao,
    private val taskPeriodsDao: TaskPeriodsDao
) : LocalDataSource {

    override suspend fun save(account: Account) =
        onIoThread { accountDao.insert(account) }

    override suspend fun isLoggedIn(): Boolean =
        onIoThread { accountDao.getFirstUser() != null }

    override suspend fun save(task: Task) =
        onIoThread { taskDao.insert(task) }

    override suspend fun saveAll(vararg task: Task) =
        onIoThread { taskDao.insertAll(*task) }

    override suspend fun account(): Account =
        onIoThread { accountDao.getFirstUser() ?: throw IllegalStateException("Not Found User") }

    override suspend fun tasks(): List<Task> =
        onIoThread { taskDao.tasks() }

    override suspend fun saveAll(vararg taskPeriods: TaskPeriods) =
        onIoThread { taskPeriodsDao.insertAll(*taskPeriods) }

    override suspend fun taskPeriodsById(id: Int): TaskPeriods =
        onIoThread {
            taskPeriodsDao.taskPeriodsById(id)
                ?: TaskPeriods(id, listOf())
        }


    private suspend fun <T> onIoThread(block: suspend () -> T) =
        withContext(ioDispatcher) { block.invoke() }

}