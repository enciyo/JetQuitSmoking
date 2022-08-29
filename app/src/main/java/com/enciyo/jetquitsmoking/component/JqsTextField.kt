package com.enciyo.jetquitsmoking.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.enciyo.jetquitsmoking.ValidationState

val VALIDATION_NAME: (String) -> Boolean = { it.length < 3 }
val VALIDATION_NOT_EMPTY: (String) -> Boolean = { it.isEmpty() }


@OptIn(ExperimentalLifecycleComposeApi::class)
@Composable
fun JqsTextValidationField(
    modifier: Modifier = Modifier,
    state: ValidationState,
    placeHolder: String,
    error: String,
    isEnable: Boolean,
    keyboardType: KeyboardType = KeyboardType.Text
) {

    val text by state.valueStateFlow.collectAsStateWithLifecycle()
    val isError by state.isError.collectAsStateWithLifecycle()

    JqsTextField(
        modifier = modifier,
        text = text,
        isError = isError,
        onValueChange = state.onValueChange,
        placeHolder = placeHolder,
        error = error,
        keyboardType = keyboardType,
        isEnable = isEnable
    )
}


@Composable
fun JqsTextField(
    modifier: Modifier = Modifier,
    text: String,
    isError: Boolean,
    onValueChange: (String) -> Unit,
    placeHolder: String,
    error: String,
    isEnable: Boolean,
    keyboardType: KeyboardType = KeyboardType.Text,
) {
    Column {
        OutlinedTextField(
            modifier = modifier.fillMaxWidth(),
            value = text,
            onValueChange = onValueChange,
            label = { Text(text = placeHolder) },
            isError = isError,
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = keyboardType),
            singleLine = true,
            shape = MaterialTheme.shapes.small,
            enabled = isEnable
        )
        if (isError)
            Text(
                text = error,
                color = MaterialTheme.colors.error,
                style = MaterialTheme.typography.caption,
                modifier = Modifier.padding(start = 16.dp, top = 0.dp)
            )
    }
}

@Preview(showBackground = true, name = "Text Field")
@Composable
fun DefaultJqsTextField() {
    JqsTextField(
        text = "Text",
        isError = false,
        onValueChange = {},
        placeHolder = "Place Holder",
        error = "Error",
        isEnable = false
    )
}

@Preview(showBackground = true, name = "Text Field Error State")
@Composable
fun DefaultErrorJqsTextField() {
    JqsTextField(
        text = "Text",
        isError = true,
        onValueChange = {},
        placeHolder = "Place Holder",
        error = "Error",
        isEnable = false
    )
}