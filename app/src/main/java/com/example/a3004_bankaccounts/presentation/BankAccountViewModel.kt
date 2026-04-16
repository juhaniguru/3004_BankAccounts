package com.example.a3004_bankaccounts.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.a3004_bankaccounts.AccountBalanceApp
import com.example.a3004_bankaccounts.domain.BankAccountAPI
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class BankAccountViewModel(private val api: BankAccountAPI) : ViewModel() {

    companion object {
        fun createFactory(): ViewModelProvider.Factory = viewModelFactory {

            initializer {
                val app =
                    this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as AccountBalanceApp
                BankAccountViewModel(app.api)
            }
        }
    }

    private val _accountsState = MutableStateFlow(AccountsState())
    val accountsState = _accountsState.asStateFlow()

    init {
        getAccounts()
    }

    fun getAccounts() {

        viewModelScope.launch {
            try {
                _accountsState.update { currentState ->
                    currentState.copy(
                        loading = true,
                        err = null
                    )
                }
                val accounts = api.getAccounts()
                _accountsState.update { currentState -> currentState.copy(items = accounts) }
            } catch (e: Exception) {
                _accountsState.update { currentState -> currentState.copy(err = e.message) }
            } finally {
                _accountsState.update { currentState -> currentState.copy(loading = false) }
            }


        }

    }


}