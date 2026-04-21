package com.example.a3004_bankaccounts.presentation

import com.example.a3004_bankaccounts.domain.DetailDataPoint

data class BankAccountDetailsState(
    val loading: Boolean = false,
    val err: String? = null,
    val dataPoints: List<DetailDataPoint> = emptyList()
)
