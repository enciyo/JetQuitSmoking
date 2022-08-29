package com.enciyo.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "account"
)
data class Account(
    @PrimaryKey(autoGenerate = false)
    var userId: Int = 0,
    val name: String,
    val cigarettesSmokedPerDay: Int,
    val cigarettesInAPack: Int,
    val pricePerPack: Int
)

