package com.example.exp23.ui.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.exp23.ui.shared.ErrorScreen
import com.example.exp23.ui.shared.LoadingScreen
import com.example.exp23.ui.shared.UiState

@Composable
fun HomeScreen(
    mainViewModel: MainViewModel = hiltViewModel()
) {
    val uiState: HomeUiState by mainViewModel.fuelAppModelFlow.collectAsState(HomeUiState())

    LaunchedEffect(uiState) {
        mainViewModel.loadMainData()
    }

    Column(
        modifier = Modifier
            .padding(24.dp)
    ) {
        Spacer(modifier = Modifier.height(60.dp))

        when (uiState.state) {
            UiState.Loading -> LoadingScreen()
            UiState.Success -> {

            }
            else -> ErrorScreen()
        }
    }
}