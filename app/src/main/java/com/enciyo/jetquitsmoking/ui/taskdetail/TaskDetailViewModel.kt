package com.enciyo.jetquitsmoking.ui.taskdetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.enciyo.data.entity.Period
import com.enciyo.data.entity.TaskPeriods
import com.enciyo.data.repo.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class TaskDetailViewModel @Inject constructor(
    private val repository: Repository,
) : ViewModel() {

    private val _state = MutableStateFlow(TaskDetailUiState())
    val state get() = _state.asStateFlow()

    fun taskId(id: Int) {
        viewModelScope.launch {
            val taskPeriods = repository.taskPeriodsById(id)
            _state.update { it.copy(taskPeriods = taskPeriods.period) }
        }
    }

}

data class TaskDetailUiState(
    val taskPeriods: List<Period> = listOf()
)