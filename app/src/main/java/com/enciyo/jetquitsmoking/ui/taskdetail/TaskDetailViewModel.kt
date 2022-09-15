package com.enciyo.jetquitsmoking.ui.taskdetail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.enciyo.data.entity.PeriodEntity
import com.enciyo.domain.dto.Period
import com.enciyo.domain.usecase.GetTaskDetailByIdUseCase
import com.enciyo.shared.DefaultDispatcher
import com.enciyo.shared.today
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.*
import javax.inject.Inject


@HiltViewModel
class TaskDetailViewModel @Inject constructor(
    private val getTaskDetailByIdUseCase: GetTaskDetailByIdUseCase,
    @DefaultDispatcher private val defaultDispatcher: CoroutineDispatcher,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private val taskId: String = checkNotNull(savedStateHandle["taskId"])
    private val clock by lazy { today().time }
    val needSmokeCount: String = checkNotNull(savedStateHandle["needSmokeCount"])

    val state =
        getTaskDetailByIdUseCase.invoke(taskId.toInt())
            .map { taskPeriods ->
                val activePeriodIndex =
                    taskPeriods.periods.indexOfFirst { it.time.time.compareTo(clock) == 1 }
                TaskDetailUiState(taskPeriods.periods, activePeriodIndex)
            }
            .filterNot { it.taskPeriodEntities.isEmpty() }
            .flowOn(defaultDispatcher)
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = TaskDetailUiState()
            )
}

data class TaskDetailUiState(
    val taskPeriodEntities: List<Period> = listOf(),
    val activePeriodIndex: Int = 0,
)
