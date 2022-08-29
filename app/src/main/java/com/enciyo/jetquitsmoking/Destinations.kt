package com.enciyo.jetquitsmoking

sealed class Destinations(val route: String) {
    object Splash : Destinations("splash")
    object Main : Destinations("main")
    object Register : Destinations("register")
    object TaskDetail : Destinations("task_detail/{taskId}/{needSmokeCount}")
}