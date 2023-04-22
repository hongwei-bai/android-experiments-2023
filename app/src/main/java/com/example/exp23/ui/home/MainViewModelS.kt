package com.example.exp23.ui.home

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModelS @Inject constructor() : ViewModel() {
    private val _uiState = MutableStateFlow(ScreenState())
    val uiState: StateFlow<ScreenState> = _uiState.asStateFlow()

    init {
        _uiState.update { currentState ->
            currentState.copy(name = "1")
        }
        GlobalScope.launch {
            delay(8000)
            _uiState.update { currentState ->
                currentState.copy(name = "2")
            }
            delay(4000)
            _uiState.update { currentState ->
                currentState.copy(name = "3")
            }
        }
    }

    fun onNameChange(newName: String) {
        _uiState.update { currentState ->
            currentState.copy(name = newName)
        }
        GlobalScope.launch {
            delay(4000)
            _uiState.update { currentState ->
                currentState.copy(name = newName + "+1")
            }
        }
    }

    fun onNameChange2(newName: String) {
        _uiState.update { currentState ->
            currentState.copy(name2 = newName)
        }
    }
}

data class ScreenState(
    val name: String = "",
    val name2: String = ""
)