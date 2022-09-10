package com.enciyo.jetquitsmoking.util

import androidx.compose.runtime.*
import com.enciyo.jetquitsmoking.component.VALIDATION_NAME
import com.enciyo.jetquitsmoking.component.VALIDATION_NOT_EMPTY
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

fun validationState(
    validator: (String) -> Boolean = VALIDATION_NOT_EMPTY,
) = ValidationState(validator)

@Stable
open class ValidationState(
    private val validator: (String) -> Boolean = { true },
) {

    private val _valueStateFlow = MutableStateFlow("")
    val valueStateFlow = _valueStateFlow.asStateFlow()

    private val _isError = MutableStateFlow(false)
    val isError = _isError.asStateFlow()

    private val _isValid = MutableStateFlow(false)
    val isValid = _isValid.asStateFlow()

    val onValueChange: (String) -> Unit = { value ->
        _valueStateFlow.value = value
        _isError.value = validator.invoke(value).also {
            _isValid.value = it.not()
        }
    }

}
