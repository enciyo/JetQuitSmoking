package com.enciyo.shared

import android.util.Log


private const val TAG_LOG = "MyLogger"

infix fun String.log(message: String) = Log.i(this, message)

fun log(message: String) = TAG_LOG log message

fun loge(message: String) = Log.e(TAG_LOG, message)

