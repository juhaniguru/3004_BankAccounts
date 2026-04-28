package com.example.a3004_bankaccounts

import android.app.Application
import com.example.a3004_bankaccounts.data.BankAccountAPIImpl
import com.example.a3004_bankaccounts.domain.BankAccountAPI
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

class AccountBalanceApp : Application() {

    val api: BankAccountAPI by lazy {

        val retro = Retrofit.Builder().baseUrl("http://10.0.2.2:5000/api/v1/").addConverterFactory(
            GsonConverterFactory.create()
        ).build()

        retro.create<BankAccountAPI>()
    }
}