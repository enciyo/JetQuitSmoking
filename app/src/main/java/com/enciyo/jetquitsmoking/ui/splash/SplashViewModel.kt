package com.enciyo.jetquitsmoking.ui.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.enciyo.data.repo.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.shareIn
import javax.inject.Inject


@HiltViewModel
class SplashViewModel @Inject constructor(
    private val accountRepository: Repository
) : ViewModel() {
    companion object {
        private const val DELAY_SPLASH = 2000L
    }

    val isLoggedIn = flow {
        val isLoggedIn = accountRepository.isLoggedIn()
        delay(DELAY_SPLASH)
        emit(isLoggedIn)
    }.shareIn(
        viewModelScope,
        started = SharingStarted.WhileSubscribed(5000)
    )


}