package com.example.exp23.ui.home

import com.example.exp23.ui.shared.UiState


data class HomeUiState(
    val state: UiState = UiState.Loading,
    val cards: List<Card> = emptyList()
)

data class Card(
    val id: Int,
    val isEnabled: Boolean,
    val isHighlight: Boolean,
    val price: String?,
    val expire: String?
)
