package com.enciyo.jetquitsmoking.ui.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.enciyo.data.repo.Repository
import com.enciyo.data.entity.Account
import com.enciyo.jetquitsmoking.component.VALIDATION_NAME
import com.enciyo.jetquitsmoking.component.VALIDATION_NOT_EMPTY
import com.enciyo.jetquitsmoking.validationState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {

    val name = validationState(validator = VALIDATION_NAME)
    val smokedPerDay = validationState(validator = VALIDATION_NOT_EMPTY)
    val countInAPack = validationState(validator = VALIDATION_NOT_EMPTY)
    val pricePerPack = validationState(validator = VALIDATION_NOT_EMPTY)

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
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            val account = Account(
                name = name.valueStateFlow.value,
                cigarettesInAPack = countInAPack.valueStateFlow.value.toInt(),
                cigarettesSmokedPerDay = smokedPerDay.valueStateFlow.value.toInt(),
                pricePerPack = pricePerPack.valueStateFlow.value.toInt()
            )
            repository.singUp(account)
            _state.update { it.copy(isLoading = false, isLoggedIn = true) }
        }
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