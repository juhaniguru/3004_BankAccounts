package com.example.a3004_bankaccounts

import android.app.Application
import com.example.a3004_bankaccounts.data.BankAccountAPIImpl
import com.example.a3004_bankaccounts.domain.BankAccountAPI

class AccountBalanceApp : Application() {

    val api : BankAccountAPI by lazy {

        BankAccountAPIImpl()
    }
}