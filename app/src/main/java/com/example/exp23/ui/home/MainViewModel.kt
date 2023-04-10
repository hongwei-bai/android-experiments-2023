package com.example.exp23.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.exp23.TestSingleton
import com.example.exp23.data.Exp23AppRepository
import com.example.exp23.data.model.Exp23AppModel
import com.example.exp23.ui.shared.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import sqip.CardEntry
import javax.inject.Inject


@HiltViewModel
class MainViewModel @Inject constructor(
    private val exp23AppRepository: Exp23AppRepository
) : ViewModel() {
    val fuelAppModelFlow = exp23AppRepository.flow.map { model ->
        when (model) {
            is Exp23AppModel -> HomeUiState(
                state = UiState.Success,
                headerName = model.header.name,
                headerDate = model.header.date,
                headerDescription = model.header.description,
                cards = model.cards.map {
                    Card(
                        name = it.name,
                        isEnabled = it.isActivated,
                        isHighlight = it.isHighlight,
                        expire = it.expire
                    )
                }
            )
            else -> HomeUiState(
                state = UiState.Error
            )
        }
    }

    fun loadMainData() {
        viewModelScope.launch(Dispatchers.IO) {
            exp23AppRepository.fetchData()
        }
    }

    fun updateMainData() {
        viewModelScope.launch(Dispatchers.IO) {
            exp23AppRepository.fetchData(false)
        }
    }

    fun makePayment() {
        CardEntry.startCardEntryActivity(TestSingleton.activity)
    }
}