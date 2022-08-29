package com.enciyo.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.enciyo.data.entity.Account

@Dao
interface AccountDao {

    @Query("Select * from account where userId == 0 ")
    suspend fun getFirstUser(): Account?

    @Insert
    suspend fun insert(account: Account)

}