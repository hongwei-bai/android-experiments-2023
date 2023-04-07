package com.example.exp23.data.model


sealed class FuelAppModelBase

data class FuelAppModel(
    val accounts: List<Account>
) : FuelAppModelBase()

object FuelAppModelError : FuelAppModelBase()

data class Account(
    val id: Int,
    val isLocked: Boolean,
    val lockedPrice: String?,
    val isHighlight: Boolean,
    val expire: String?
)