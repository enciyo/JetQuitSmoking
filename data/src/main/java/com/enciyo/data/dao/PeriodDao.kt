package com.enciyo.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.enciyo.data.entity.Period


@Dao
interface PeriodDao {

    @Query("Select * from Period")
    @SuppressWarnings("unUseless")
    fun getPeriods(): List<Period>

    @Insert
    fun insert(period: Period)

    @Insert
    fun insert(vararg period: Period)

    @Delete
    fun delete(period: Period)

}