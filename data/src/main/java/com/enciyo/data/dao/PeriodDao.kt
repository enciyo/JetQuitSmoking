package com.enciyo.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.enciyo.data.entity.PeriodEntity


@Dao
interface PeriodDao {

    @Query("Select * from PeriodEntity")
    @SuppressWarnings("unUseless")
    fun getPeriods(): List<PeriodEntity>

    @Insert
    fun insert(periodEntity: PeriodEntity)

    @Insert
    fun insert(vararg periodEntity: PeriodEntity) : List<Long>

    @Delete
    fun delete(periodEntity: PeriodEntity)

}