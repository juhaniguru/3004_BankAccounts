package com.example.a3004_bankaccounts.presentation

import com.example.a3004_bankaccounts.domain.BankAccount


data class AccountsState(
    val loading: Boolean = false,
    val err: String? = null,
    val items: List<BankAccount> = emptyList()
)
