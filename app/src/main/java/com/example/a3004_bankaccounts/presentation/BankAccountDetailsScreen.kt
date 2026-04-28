package com.example.a3004_bankaccounts.presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
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



    BankAccountDetailsScreen(state = state, modelProducer = modelProducer)
}

@Composable
fun BankAccountDetailsScreen(
    modifier: Modifier = Modifier,
    state: BankAccountDetailsState,
    modelProducer: CartesianChartModelProducer

) {
    Scaffold(bottomBar = {
        BottomAppBar() {
            TextButton(onClick = {}) {

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