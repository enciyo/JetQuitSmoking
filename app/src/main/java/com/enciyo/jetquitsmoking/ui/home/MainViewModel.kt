package com.enciyo.jetquitsmoking.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.enciyo.data.entity.Account
import com.enciyo.data.entity.Task
import com.enciyo.data.repo.Repository
import com.enciyo.shared.today
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.datetime.LocalDate
import javax.inject.Inject


@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: Repository,
) : ViewModel() {

    val state = combine(
        flow = repository.tasks(),
        flow2 = repository.account().map { it.name },
        transform = ::MainUiState
    )
        .stateIn(
            viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = MainUiState()
        )
}

data class MainUiState(
    val tasks: List<Task> = listOf(),
    val userName: String = "",
    val today: LocalDate = today().date,
)