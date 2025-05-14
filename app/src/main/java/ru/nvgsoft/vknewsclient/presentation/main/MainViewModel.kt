package ru.nvgsoft.vknewsclient.presentation.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.nvgsoft.vknewsclient.domain.usecases.CheckAuthUseCase
import ru.nvgsoft.vknewsclient.domain.usecases.GetAuthStateUseCase
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val checkAuthUseCase: CheckAuthUseCase,
    private val getAuthStateUseCase: GetAuthStateUseCase
) : ViewModel() {


    val authState = getAuthStateUseCase()

    init {
        viewModelScope.launch {
            checkAuthUseCase()

        }

    }


}

