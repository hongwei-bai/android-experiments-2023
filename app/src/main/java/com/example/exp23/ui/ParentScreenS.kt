package com.example.exp23.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.exp23.ui.home.MainViewModelS

@Composable
fun ParentScreen(
    mainViewModelS: MainViewModelS = viewModel()
) {
    val state by mainViewModelS.uiState.collectAsState()

    Column(modifier = Modifier.fillMaxSize().padding(64.dp)) {
//        Text(text = state.name, modifier = Modifier.fillMaxSize())
//            .width(100.dp).height(50.dp))
//        Text(text = state.name2, modifier = Modifier.width(100.dp).height(50.dp))
        ChildScreen1(
            name = state.name,
            onNameChange = mainViewModelS::onNameChange
        )
        ChildScreen2(
            name =state.name2,
            onNameChange = mainViewModelS::onNameChange2
        )
    }
}

@Composable
fun ChildScreen1(
    name: String,
    onNameChange: (String) -> Unit
) {
    OutlinedTextField(
        value = name,
        onValueChange =
//        {},
        onNameChange,
        label = { Text(text = "Name1") }
    )
}

@Composable
fun ChildScreen2(
    name: String,
    onNameChange: (String) -> Unit
) {
    OutlinedTextField(
        value = name,
        onValueChange =
//        {},
        onNameChange,
        label = { Text(text = "Name2") }
    )
}