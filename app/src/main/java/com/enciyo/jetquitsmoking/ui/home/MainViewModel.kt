package com.enciyo.jetquitsmoking.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.enciyo.domain.dto.Task
import com.enciyo.domain.usecase.GetTasksUseCase
import com.enciyo.domain.usecase.GetUsernameUseCase
import com.enciyo.shared.today
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.datetime.LocalDate
import javax.inject.Inject


@HiltViewModel
class MainViewModel @Inject constructor(
    private val getTasksUseCase: GetTasksUseCase,
    private val getUsernameUseCase: GetUsernameUseCase,
) : ViewModel() {

    val state = combine(
        flow = getTasksUseCase.invoke(Unit),
        flow2 = getUsernameUseCase.invoke(Unit),
        transform = ::MainUiState
    )
        .stateIn(
            viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = MainUiState()
        )
}

data class MainUiState(
    val task: List<Task> = listOf(),
    val userName: String = "",
    val today: LocalDate = today().date,
)