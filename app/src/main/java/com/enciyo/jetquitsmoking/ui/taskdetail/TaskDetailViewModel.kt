package com.enciyo.jetquitsmoking.ui.taskdetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.enciyo.data.entity.Period
import com.enciyo.data.repo.Repository
import com.enciyo.shared.DefaultDispatcher
import com.enciyo.shared.today
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject


@HiltViewModel
class TaskDetailViewModel @Inject constructor(
    private val repository: Repository,
    @DefaultDispatcher private val defaultDispatcher: CoroutineDispatcher,
) : ViewModel() {

    private val _state = MutableStateFlow(TaskDetailUiState())
    val state get() = _state.asStateFlow()


    fun taskId(id: Int) {
        viewModelScope.launch {
            val taskPeriods = repository.taskPeriodsById(id)
            if (taskPeriods.periods.isEmpty()) return@launch
            val clock = today().time
            val activePeriodIndex = withContext(defaultDispatcher) {
                taskPeriods.periods.indexOfFirst { it.time.time.compareTo(clock) == 1 }
            }

            _state.update {
                it.copy(
                    taskPeriods = taskPeriods.periods,
                    activePeriodIndex = activePeriodIndex
                )
            }
        }
    }

}

data class TaskDetailUiState(
    val taskPeriods: List<Period> = listOf(),
    val activePeriodIndex: Int = 0,
)
