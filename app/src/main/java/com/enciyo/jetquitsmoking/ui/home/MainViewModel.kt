package com.enciyo.jetquitsmoking.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.enciyo.data.entity.Account
import com.enciyo.data.entity.Task
import com.enciyo.data.repo.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {

    private val _state = MutableStateFlow(MainUiState())
    val state get() = _state.asStateFlow()

    init {
        tasks()
        account()
    }

    private fun tasks() {
        viewModelScope.launch {
            val tasks = repository.tasks()
            _state.update { it.copy(tasks = tasks) }
        }
    }

    private fun account() {
        viewModelScope.launch {
            val account = repository.account()
            _state.update { it.copy(account = account) }
        }
    }

}

data class MainUiState(
    val tasks: List<Task> = listOf(),
    val account: Account? = null
)