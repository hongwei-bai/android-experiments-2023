package com.example.exp23.data.model


sealed class Exp23AppModelBase

data class Exp23AppModel(
    val header: Header,
    val cards: List<Card>
) : Exp23AppModelBase()

object Exp23AppModelError : Exp23AppModelBase()

data class Header(
    val name: String,
    val date: String,
    val description: String
)

data class Card(
    val id: Int,
    val name: String,
    val isActivated: Boolean,
    val isHighlight: Boolean,
    val expire: String
)