package com.example.a3004_bankaccounts.presentation

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
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

class BankAccountDetailsViewModel(
    private val api: BankAccountAPI,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    companion object {
        fun createFactory(): ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val app =
                    this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as AccountBalanceApp
                val savedStateHandle = createSavedStateHandle()
                BankAccountDetailsViewModel(app.api, savedStateHandle)
            }
        }
    }

    val accountId = savedStateHandle.getStateFlow("accountId", "")

    private val _state = MutableStateFlow(BankAccountDetailsState())
    val state = _state.asStateFlow()

    init {
        getDetails()
    }

    fun getDetails() {
        viewModelScope.launch {
            try {
                _state.update { currentState -> currentState.copy(loading = true, err = null) }
                delay(1500)
                val dataPoints = api.getAccountDetails(accountId.value.toInt())
                _state.update { currentState -> currentState.copy(dataPoints = dataPoints) }

            } catch (e: Exception) {

                _state.update { currentState -> currentState.copy(err = e.message) }

            } finally {
                _state.update { currentState -> currentState.copy(loading = false) }
            }
        }
    }


}