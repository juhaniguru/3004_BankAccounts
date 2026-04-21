package com.example.a3004_bankaccounts.data

import com.example.a3004_bankaccounts.domain.BankAccount
import com.example.a3004_bankaccounts.domain.BankAccountAPI
import com.example.a3004_bankaccounts.domain.DetailDataPoint


class BankAccountAPIImpl : BankAccountAPI {
    override suspend fun getAccounts(): List<BankAccount> {
        return listOf(BankAccount(1, "FI-123456", "Koti"), BankAccount(id = 6, "accountnumber", description = "Koti"))
    }

    override suspend fun getAccountDetails(accountId: Int): List<DetailDataPoint> {
        return listOf(DetailDataPoint(
            dt = "2026-01-01",
            value = 1f
        ),DetailDataPoint(
            dt = "2026-02-01",
            value = 2f
        ),DetailDataPoint(
            dt = "2026-03-01",
            value = 3f
        ),DetailDataPoint(
            dt = "2026-04-01",
            value = 4f
        ),DetailDataPoint(
            dt = "2026-05-01",
            value = 5f
        ),DetailDataPoint(
            dt = "2026-06-01",
            value = 6f
        ),DetailDataPoint(
            dt = "2026-07-01",
            value = 7f
        ),DetailDataPoint(
            dt = "2026-08-01",
            value = 8f
        ),DetailDataPoint(
            dt = "2026-09-01",
            value = 9f
        ),DetailDataPoint(
            dt = "2026-10-01",
            value = 10f
        ),DetailDataPoint(
            dt = "2026-11-01",
            value = 11f
        ),DetailDataPoint(
            dt = "2026-12-01",
            value = 12f
        ),)
    }
}