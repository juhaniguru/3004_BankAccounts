package com.example.a3004_bankaccounts.domain


interface BankAccountAPI {
    suspend fun getAccounts() : List<BankAccount>

    suspend fun getAccountDetails(accountId: Int) : List<DetailDataPoint>
}