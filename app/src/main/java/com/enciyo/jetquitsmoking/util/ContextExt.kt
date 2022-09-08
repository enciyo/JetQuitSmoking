package com.enciyo.jetquitsmoking.util

import android.content.Context
import android.content.Intent

inline fun <reified T> Context.intent() = Intent(this, T::class.java)