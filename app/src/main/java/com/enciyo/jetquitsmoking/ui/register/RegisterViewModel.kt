package com.enciyo.jetquitsmoking.ui.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.enciyo.domain.dto.Account
import com.enciyo.domain.usecase.SaveAccountUseCase
import com.enciyo.jetquitsmoking.component.VALIDATION_NAME
import com.enciyo.jetquitsmoking.util.validationState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import javax.inject.Inject


@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val saveAccountUseCase: SaveAccountUseCase,
) : ViewModel() {

    val name = validationState(validator = VALIDATION_NAME)
    val smokedPerDay = validationState()
    val countInAPack = validationState()
    val pricePerPack = validationState()

    private val _state = MutableStateFlow(RegisterUiState())
    val state = _state.asStateFlow()

    init {
        combine(
            flow = name.isValid,
            flow2 = smokedPerDay.isValid,
            flow3 = countInAPack.isValid,
            flow4 = pricePerPack.isValid,
        ) { one, two, three, four -> listOf(one, two, three, four).none { it.not() } }
            .onEach { isValid -> _state.update { it.copy(isFormValid = isValid) } }
            .launchIn(viewModelScope)
    }


    fun onEvent(event: RegisterUiEvent) {
        when (event) {
            RegisterUiEvent.OnSave -> onSave()
        }
    }

    private fun onSave() {
        flow {
            val account = Account(
                name = name.valueStateFlow.value,
                cigarettesInAPack = countInAPack.valueStateFlow.value.toInt(),
                cigarettesSmokedPerDay = smokedPerDay.valueStateFlow.value.toInt(),
                pricePerPack = pricePerPack.valueStateFlow.value.toInt()
            )
            emit(account)
        }
            .flatMapConcat(saveAccountUseCase::invoke)
            .onStart {
                _state.update { it.copy(isLoading = true) }
            }
            .onCompletion {
                _state.update { it.copy(isLoading = false, isLoggedIn = true) }
            }
            .catch {
                //Firebase Log
            }
            .launchIn(viewModelScope)
    }

}


data class RegisterUiState(
    val isLoading: Boolean = false,
    val isLoggedIn: Boolean = false,
    val isFormValid: Boolean = false,
) {
    val isInputsEnable get() = isLoading.not()
    val isSaveBtnEnable get() = isFormValid
}


sealed interface RegisterUiEvent {
    object OnSave : RegisterUiEvent
}