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

/*
* In my compose app, what could be the reasonsmy parent layout always got recomposed when only one of children views got data updated?

chatGPT:
There are a few potential reasons why your parent layout might be getting recomposed even if only one of its children views has been updated:

Dependency chain: If your parent layout depends on the state or properties of its children views, any change to those properties can trigger a recomposition of the parent layout. This is because the parent layout needs to reevaluate its own state and properties based on the new state of its children views. If you have a long dependency chain, where one change triggers multiple recompositions up the chain, it can lead to performance issues.

Modifier dependencies: If any of the children views have modifiers that depend on the state or properties of the parent layout, this can also trigger a recomposition of the parent layout. For example, if you have a Box layout with a padding modifier that depends on the height of a child view, any change to the height of that child view can trigger a recomposition of the Box layout.

Incorrect scoping of state: If you have state that should only be updated within a child view, but is instead updated at the parent level, this can trigger a recomposition of the entire parent layout. Make sure that your state is scoped appropriately and only updated where necessary.

To solve this issue, you can try to minimize the dependencies between your parent and child views, and ensure that state is scoped correctly. One way to do this is to use remember to store state at the appropriate level, and pass it down to child views via parameters. You can also use key to help Compose understand when a child view has been updated in a way that doesn't affect the parent layout. Finally, you can use the CompositionLocalProvider to pass down state to child views in a more localized and scoped way.
*/
@Composable
fun ParentScreen(
    mainViewModelS: MainViewModelS = viewModel()
) {
//    val state by mainViewModelS.uiState.collectAsState()
//    val state1 by mainViewModelS.uiState1.collectAsState()
//    val state2 by mainViewModelS.uiState2.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(64.dp)
    ) {
//        Text(text = state.name)
//        Text(text = state.name2)
//        Text(text = state1)
//        Text(text = state2)
//        ChildScreen1(
//            name = state.name,
//            onNameChange = mainViewModelS::onNameChange
//        )
//        ChildScreen2(
//            name =state.name2,
//            onNameChange = mainViewModelS::onNameChange2
//        )

        ChildScreen1Hongwei()
        ChildScreen2Hongwei()
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

@Composable
fun ChildScreen1Hongwei(
    mainViewModelS: MainViewModelS = viewModel()
) {
    val state1 by mainViewModelS.uiState1.collectAsState()
    OutlinedTextField(
        value = state1,
        onValueChange =
        {},
//        onNameChange,
        label = { Text(text = "Name1") }
    )
}

@Composable
fun ChildScreen2Hongwei(
    mainViewModelS: MainViewModelS = viewModel()
) {
    val state2 by mainViewModelS.uiState2.collectAsState()
    OutlinedTextField(
        value = state2,
        onValueChange =
        {},
//        onNameChange,
        label = { Text(text = "Name1") }
    )
}