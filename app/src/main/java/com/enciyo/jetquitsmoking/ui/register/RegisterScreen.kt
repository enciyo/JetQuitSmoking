package com.enciyo.jetquitsmoking.ui.register

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.enciyo.jetquitsmoking.R
import com.enciyo.jetquitsmoking.component.JqsTextValidationField
import com.enciyo.jetquitsmoking.ui.theme.JetQuitSmokingTheme
import com.enciyo.jetquitsmoking.ui.theme.paddings

@OptIn(ExperimentalLifecycleComposeApi::class)
@Composable
fun RegisterScreen(
    modifier: Modifier = Modifier,
    vm: RegisterViewModel = hiltViewModel(),
    navigateToMain: () -> Unit
) {
    val state by vm.state.collectAsStateWithLifecycle()
    val currentNavigateToMain by rememberUpdatedState(navigateToMain)


    LaunchedEffect(state) {
        if (state.isLoggedIn) currentNavigateToMain()
    }

    LaunchedEffect(Unit){
        vm.name.onValueChange.invoke("name")
        vm.pricePerPack.onValueChange.invoke("2")
        vm.countInAPack.onValueChange.invoke("2")
        vm.smokedPerDay.onValueChange.invoke("900")
        vm.onEvent(RegisterUiEvent.OnSave)
    }


    Box(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(
                vertical = MaterialTheme.paddings.itemSpace,
                horizontal = MaterialTheme.paddings.pagePadding
            )
    ) {
        Column(
            modifier = Modifier.align(Alignment.Center),
            verticalArrangement = Arrangement.spacedBy(MaterialTheme.paddings.itemSpace),
        ) {

            Header()

            JqsTextValidationField(
                state = vm.name,
                placeHolder = stringResource(R.string.name),
                error = stringResource(id = R.string.validation_error_name),
                isEnable = state.isInputsEnable
            )
            JqsTextValidationField(
                state = vm.smokedPerDay,
                placeHolder = stringResource(R.string.cigarettes_smoked_per_day),
                error = stringResource(id = R.string.validation_error_require_not_empty),
                keyboardType = KeyboardType.Number,
                isEnable = state.isInputsEnable
            )
            JqsTextValidationField(
                state = vm.countInAPack,
                placeHolder = stringResource(R.string.cigarettes_in_a_pack),
                error = stringResource(id = R.string.validation_error_require_not_empty),
                keyboardType = KeyboardType.Number,
                isEnable = state.isInputsEnable
            )
            JqsTextValidationField(
                state = vm.pricePerPack,
                placeHolder = stringResource(R.string.price_per_pack),
                error = stringResource(id = R.string.validation_error_require_not_empty),
                keyboardType = KeyboardType.Number,
                isEnable = state.isInputsEnable
            )

            if (state.isLoading)
                CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
            else
                SaveButton(enabled = vm.state.value.isSaveBtnEnable) { vm.onEvent(RegisterUiEvent.OnSave) }
        }
    }
}

@Composable
private fun ColumnScope.SaveButton(
    modifier: Modifier = Modifier,
    enabled: Boolean,
    onClick: () -> Unit
) {
    OutlinedButton(
        onClick = onClick,
        shape = RoundedCornerShape(bottomEndPercent = 10, bottomStartPercent = 50),
        modifier = modifier
            .align(Alignment.CenterHorizontally)
            .fillMaxWidth(),
        colors = ButtonDefaults.buttonColors(
            backgroundColor = MaterialTheme.colors.primary,
            disabledBackgroundColor = MaterialTheme.colors.primary.copy(alpha = 0.7f),
            disabledContentColor = MaterialTheme.colors.onPrimary
        ),
        enabled = enabled
    ) {
        Text(text = stringResource(R.string.save))
    }
}

@Composable
private fun Header(modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
            .background(
                color = MaterialTheme.colors.primary,
                shape = RoundedCornerShape(
                    topStartPercent = 10,
                    topEndPercent = 50
                )
            )
            .fillMaxWidth()

    ) {
        Column(
            modifier = Modifier
                .padding(horizontal = MaterialTheme.paddings.pagePadding)
                .align(Alignment.CenterVertically),
            verticalArrangement = Arrangement.spacedBy(MaterialTheme.paddings.itemSpace),
        ) {
            Text(
                text = stringResource(R.string.sign_up),
                fontSize = MaterialTheme.typography.h5.fontSize,
                color = MaterialTheme.colors.onPrimary,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = stringResource(R.string.sign_up_desc),
                fontSize = MaterialTheme.typography.body2.fontSize,
                color = MaterialTheme.colors.onPrimary
            )
        }
        Animation()
    }
}


@Composable
private fun Animation(modifier: Modifier = Modifier) {
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.sign_up))
    Box(modifier = modifier.size(150.dp)) {
        LottieAnimation(
            composition = composition,
            iterations = LottieConstants.IterateForever,
            contentScale = ContentScale.Crop
        )
    }
}

@Preview(
    showBackground = true,
    device = Devices.PIXEL_4_XL,
    showSystemUi = true
)
@Composable
fun RegisterPreview() {
    JetQuitSmokingTheme {
        RegisterScreen {

        }
    }
}
