package com.enciyo.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.enciyo.data.entity.AccountEntity

@Dao
interface AccountDao {

    @Query("Select * from account where userId == 0 ")
    suspend fun getFirstUser(): AccountEntity?

    @Insert
    suspend fun insert(account: AccountEntity) : Long

}