package com.enciyo.domain.dto


data class Account(
    val name: String,
    val cigarettesSmokedPerDay: Int,
    val cigarettesInAPack: Int,
    val pricePerPack: Int,
)