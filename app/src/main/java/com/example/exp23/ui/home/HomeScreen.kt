package com.example.exp23.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.exp23.BuildConfig
import com.example.exp23.ui.shared.ErrorScreen
import com.example.exp23.ui.shared.LoadingScreen
import com.example.exp23.ui.shared.UiState

@Composable
fun HomeScreen(
    mainViewModel: MainViewModel = hiltViewModel()
) {
    val uiState: HomeUiState by mainViewModel.fuelAppModelFlow.collectAsState(HomeUiState())

    LaunchedEffect(true) {
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
                Text(
                    text = uiState.headerName ?: "",
                    style = MaterialTheme.typography.headlineMedium
                )
                Text(uiState.headerDate ?: "")
                Text(uiState.headerDescription ?: "")
                Spacer(modifier = Modifier.requiredHeight(48.dp))
                uiState.cards.forEach { card ->
                    val cardColor = if (card.isHighlight) {
                        Color.Cyan
                    } else {
                        Color.LightGray
                    }
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                            .background(cardColor)
                    ) {
                        Text(card.name)
                        Row {
                            Text(if (card.isEnabled) "Activated!" else "-")
                            Spacer(modifier = Modifier.requiredWidth(20.dp))
                            Text(if (card.isHighlight) "HIGHLIGHTED" else "-")
                        }
                        Text(card.expire ?: "")
                    }
                }
                Spacer(modifier = Modifier.requiredHeight(32.dp))
                Button(onClick = { mainViewModel.updateMainData() }) {
                    Text(text = "Update Api")
                }

                Spacer(modifier = Modifier.requiredHeight(24.dp))
                val paymentButtonText =
                    if (BuildConfig.DEBUG) "[Sandbox]Payment" else "Payment $1!!"
                Button(onClick = { mainViewModel.makePayment() }) {
                    Text(text = paymentButtonText)
                }
            }
            else -> ErrorScreen()
        }
    }
}