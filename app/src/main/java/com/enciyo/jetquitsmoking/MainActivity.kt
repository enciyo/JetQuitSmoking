package com.enciyo.jetquitsmoking

import android.os.Bundle
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.enciyo.jetquitsmoking.ui.home.MainScreen
import com.enciyo.jetquitsmoking.ui.register.RegisterScreen
import com.enciyo.jetquitsmoking.ui.splash.SplashScreen
import com.enciyo.jetquitsmoking.ui.taskdetail.TaskDetailScreen
import com.enciyo.jetquitsmoking.ui.theme.JetQuitSmokingTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)

        setContent {
            JetQuitSmokingTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    NavGraph()
                }
            }
        }

    }
}


@Composable
fun NavGraph(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
) {
    NavHost(
        navController = navController,
        modifier = modifier,
        startDestination = Destinations.Splash.route
    ) {
        composable(Destinations.Splash.route) {
            SplashScreen(
                onNavigateMain = {
                    navController.navigate(Destinations.Main.route) {
                        popUpTo(Destinations.Splash.route) { inclusive = true }
                    }
                },
                onNavigateRegister = {
                    navController.navigate(Destinations.Register.route) {
                        popUpTo(Destinations.Splash.route) { inclusive = true }
                    }
                }
            )
        }
        composable(Destinations.Main.route) {
            MainScreen {
                navController.navigate("task_detail/${it.taskId}/${it.needSmokeCount}")
            }
        }
        composable(Destinations.Register.route) {
            RegisterScreen {
                navController.navigate(Destinations.Main.route) {
                    popUpTo(Destinations.Register.route) { inclusive = true }
                }
            }
        }
        composable(Destinations.TaskDetail.route) { TaskDetailScreen() }
    }
}


@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    JetQuitSmokingTheme {
        SplashScreen(onNavigateRegister = { }) {

        }
    }
}