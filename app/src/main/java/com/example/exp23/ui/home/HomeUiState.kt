package com.example.exp23.ui.home

import com.example.exp23.ui.shared.UiState


data class HomeUiState(
    val state: UiState = UiState.Loading,
    val headerName: String? = null,
    val headerDate: String? = null,
    val headerDescription: String? = null,
    val cards: List<Card> = emptyList()
)

data class Card(
    val name: String,
    val isEnabled: Boolean,
    val isHighlight: Boolean,
    val expire: String?
)
