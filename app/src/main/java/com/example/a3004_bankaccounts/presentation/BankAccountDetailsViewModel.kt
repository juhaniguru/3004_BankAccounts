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
import com.example.a3004_bankaccounts.domain.DetailDataPoint
import com.patrykandpatrick.vico.compose.cartesian.data.CartesianChartModelProducer
import com.patrykandpatrick.vico.compose.cartesian.data.columnSeries
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


    // CartesianChartModelProducer on se objekti, joka
    // tuottaa AccountEvent-objekteista x,y-koordinnaatteja
    val modelProducer = CartesianChartModelProducer()

    init {
        getDetails()
    }

    fun toggleDatePicker() {
        val newValue = !state.value.showDatePicker
        _state.update { currentState -> currentState.copy(showDatePicker = newValue) }
    }

    fun updateChart(dataPoints: List<DetailDataPoint>) {
        viewModelScope.launch {
            modelProducer.runTransaction {
                columnSeries {
                    val months = FloatArray(12)
                    dataPoints.forEachIndexed { index, dp ->
                        months[index] = dp.value
                    }

                    series(months.toList())


                }
            }
        }
    }

    fun getDetails() {
        viewModelScope.launch {
            try {
                _state.update { currentState -> currentState.copy(loading = true, err = null) }
                delay(1500)
                val dataPoints = api.getAccountDetails(accountId.value.toInt())
                updateChart(dataPoints)
                _state.update { currentState -> currentState.copy(dataPoints = dataPoints) }

            } catch (e: Exception) {

                _state.update { currentState -> currentState.copy(err = e.message) }

            } finally {
                _state.update { currentState -> currentState.copy(loading = false) }
            }
        }
    }


}