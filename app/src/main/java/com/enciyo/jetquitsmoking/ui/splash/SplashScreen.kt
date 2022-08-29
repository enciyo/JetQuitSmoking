package com.enciyo.jetquitsmoking.ui.splash

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.enciyo.jetquitsmoking.R
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@Composable
fun SplashScreen(
    modifier: Modifier = Modifier,
    vm: SplashViewModel = hiltViewModel(),
    onNavigateRegister: () -> Unit,
    onNavigateMain: () -> Unit
) {
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.lottie_smoking))
    val padding = LocalConfiguration.current.screenWidthDp / 5
    val currentOnNavigateRegister by rememberUpdatedState(onNavigateRegister)
    val currentOnNavigateMain by rememberUpdatedState(onNavigateMain)

    LaunchedEffect(Unit) {
        vm.isLoggedIn
            .onEach { if (it) currentOnNavigateMain.invoke() else currentOnNavigateRegister.invoke() }
            .launchIn(this)
    }

    Box(
        modifier = modifier
            .background(MaterialTheme.colors.primary)
            .fillMaxSize()
    ) {
        LottieAnimation(
            composition,
            iterations = LottieConstants.IterateForever,
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .align(Alignment.Center)
                .padding(horizontal = padding.dp)
                .aspectRatio(1f),
        )
    }

}
