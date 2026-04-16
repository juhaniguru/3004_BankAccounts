package com.example.a3004_bankaccounts.data

import com.example.a3004_bankaccounts.domain.BankAccount
import com.example.a3004_bankaccounts.domain.BankAccountAPI


class BankAccountAPIImpl : BankAccountAPI {
    override suspend fun getAccounts(): List<BankAccount> {
        return listOf(BankAccount(1, "FI-123456", "Koti"))
    }
}