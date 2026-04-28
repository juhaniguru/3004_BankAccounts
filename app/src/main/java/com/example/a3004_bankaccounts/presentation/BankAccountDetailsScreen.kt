package com.example.a3004_bankaccounts.presentation

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DatePicker
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.patrykandpatrick.vico.compose.cartesian.CartesianChartHost
import com.patrykandpatrick.vico.compose.cartesian.axis.HorizontalAxis
import com.patrykandpatrick.vico.compose.cartesian.axis.VerticalAxis
import com.patrykandpatrick.vico.compose.cartesian.data.CartesianChartModelProducer
import com.patrykandpatrick.vico.compose.cartesian.data.CartesianValueFormatter
import com.patrykandpatrick.vico.compose.cartesian.layer.rememberColumnCartesianLayer
import com.patrykandpatrick.vico.compose.cartesian.rememberCartesianChart

@Composable
fun BankAccountDetailsScreenRoot(modifier: Modifier = Modifier) {

    val vm =
        viewModel<BankAccountDetailsViewModel>(factory = BankAccountDetailsViewModel.createFactory())
    val modelProducer = vm.modelProducer

    val state by vm.state.collectAsStateWithLifecycle()



    BankAccountDetailsScreen(state = state, modelProducer = modelProducer, onToggleDatePicker = {
        vm.toggleDatePicker()
    })
}

@Composable
fun BankAccountDetailsScreen(
    modifier: Modifier = Modifier,
    state: BankAccountDetailsState,
    modelProducer: CartesianChartModelProducer,
    onToggleDatePicker: () -> Unit

) {

    val dateLabel = remember(state.datePickerDate) {

        val date = java.time.Instant.ofEpochMilli(state.datePickerDate)
            .atZone(java.time.ZoneOffset.UTC)
            .toLocalDate()

        val locale = java.util.Locale.getDefault()
        val monthName = date.month.getDisplayName(java.time.format.TextStyle.FULL, locale)

        // teksti vaihtuu sen mukaan, missä vaiheessa valintaa ollaan


        "${date.dayOfMonth} $monthName ${date.year}"


    }


    Scaffold(bottomBar = {
        BottomAppBar() {
            TextButton(onClick = {
                onToggleDatePicker()
            }) {
                Icon(Icons.Default.DateRange, contentDescription = "")
                Spacer(Modifier.width(8.dp))
                Text(dateLabel)
            }
        }
    }) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            contentAlignment = Alignment.Center
        ) {
            when {
                state.loading -> CircularProgressIndicator()
                state.err != null -> Text(state.err)
                else -> DetailsGraph(modelProducer = modelProducer)
            }
        }

        if(state.showDatePicker) {
            CalendarView(initialDate = state.datePickerDate, onSetSelectedDay = {})
        }
    }
}


@Composable
fun DetailsGraph(modifier: Modifier = Modifier, modelProducer: CartesianChartModelProducer) {
    CartesianChartHost(
        chart = rememberCartesianChart(

            // koska käytämme columnSeriesia viewmodelissa
            // luomme sille layerin
            rememberColumnCartesianLayer(),

            // startAxis on chartin vasen y-akseli
            startAxis = VerticalAxis.rememberStart(),
            // x-akseli
            bottomAxis = HorizontalAxis.rememberBottom(
                // valueFormatterilla voi muokata x-akselilla näkyviä tekstejä
                valueFormatter = CartesianValueFormatter { _, value, _ ->
                    val index = value.toInt()

                        // koska x-akselin arvot alkavat nollasta,
                        // muokataan niitä valitun stepin mukaan
                        "${index + 1}"


                }
            ),
        ),
        modelProducer = modelProducer,
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CalendarView(initialDate: Long, onSetSelectedDay: (Long?) -> Unit) {
    // We create a DatePickerState anchored to the selected year/month


    val datePickerState = rememberDatePickerState(
        initialDisplayedMonthMillis = initialDate
    )
    // tee tähän päivän valinta
    LaunchedEffect(datePickerState.selectedDateMillis) {
        Log.d("juhanitestaa", datePickerState.selectedDateMillis.toString())
    }

    DatePicker(state = datePickerState, showModeToggle = false)
}