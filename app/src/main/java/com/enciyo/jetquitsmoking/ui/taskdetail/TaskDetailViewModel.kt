package com.enciyo.jetquitsmoking.ui.taskdetail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.enciyo.data.entity.Period
import com.enciyo.data.repo.Repository
import com.enciyo.jetquitsmoking.Destinations
import com.enciyo.jetquitsmoking.ui.home.MainUiState
import com.enciyo.shared.DefaultDispatcher
import com.enciyo.shared.today
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject


@HiltViewModel
class TaskDetailViewModel @Inject constructor(
    private val repository: Repository,
    @DefaultDispatcher private val defaultDispatcher: CoroutineDispatcher,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private val taskId: String = checkNotNull(savedStateHandle["taskId"])
    private val clock by lazy { today().time }
    val needSmokeCount: String = checkNotNull(savedStateHandle["needSmokeCount"])

    val state =
        repository.taskPeriodsById(taskId.toInt())
            .map { taskPeriods ->
                val activePeriodIndex =
                    taskPeriods.periods.indexOfFirst { it.time.time.compareTo(clock) == 1 }
                TaskDetailUiState(taskPeriods.periods, activePeriodIndex)
            }
            .filterNot { it.taskPeriods.isEmpty() }
            .flowOn(defaultDispatcher)
            .stateIn(
                viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                TaskDetailUiState()
            )
}

data class TaskDetailUiState(
    val taskPeriods: List<Period> = listOf(),
    val activePeriodIndex: Int = 0,
)
