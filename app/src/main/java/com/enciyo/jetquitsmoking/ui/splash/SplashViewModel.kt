package com.enciyo.jetquitsmoking.ui.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.enciyo.domain.Repository
import com.enciyo.domain.usecase.UserIsLoggedInUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.shareIn
import javax.inject.Inject


@HiltViewModel
class SplashViewModel @Inject constructor(
    userIsLoggedInUseCase: UserIsLoggedInUseCase,
) : ViewModel() {

    val isLoggedIn = userIsLoggedInUseCase.invoke(Unit)
        .shareIn(
            viewModelScope,
            started = SharingStarted.WhileSubscribed(5000)
        )

}