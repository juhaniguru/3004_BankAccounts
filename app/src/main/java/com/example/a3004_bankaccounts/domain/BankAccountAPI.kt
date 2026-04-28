package com.example.a3004_bankaccounts.domain

import retrofit2.http.GET
import retrofit2.http.Path


interface BankAccountAPI {
    @GET("accounts")
    suspend fun getAccounts(): List<BankAccount>

    @GET("accounts/{accountId}/events/{dt}/{step}")
    suspend fun getAccountDetails(
        @Path("accountId") accountId: Int,
        @Path("dt") dt: Long,
        @Path("step") step: String
    ): List<DetailDataPoint>
}