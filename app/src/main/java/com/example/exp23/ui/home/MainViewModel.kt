package com.example.exp23.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.exp23.data.Exp23AppRepository
import com.example.exp23.ui.shared.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val exp23AppRepository: Exp23AppRepository
) : ViewModel() {
    val fuelAppModelFlow = exp23AppRepository.flow.map {
        HomeUiState(
            state = UiState.Success,
            cards = emptyList()
        )
    }

    fun loadMainData() {
        viewModelScope.launch(Dispatchers.IO) {
            exp23AppRepository.fetchData()
        }
    }
}