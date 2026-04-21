package com.example.a3004_bankaccounts.presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun BankAccountDetailsScreenRoot(modifier: Modifier = Modifier) {

    val vm =
        viewModel<BankAccountDetailsViewModel>(factory = BankAccountDetailsViewModel.createFactory())

    val state by vm.state.collectAsStateWithLifecycle()

    val accountId by vm.accountId.collectAsStateWithLifecycle()

    BankAccountDetailsScreen(state = state, accountId = accountId)
}

@Composable
fun BankAccountDetailsScreen(
    modifier: Modifier = Modifier,
    state: BankAccountDetailsState,
    accountId: String
) {
    Scaffold() { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            contentAlignment = Alignment.Center
        ) {
            when {
                state.loading -> CircularProgressIndicator()
                state.err != null -> Text(state.err)
                else -> DetailsGraph(accountId = accountId)
            }
        }
    }
}


@Composable
fun DetailsGraph(modifier: Modifier = Modifier, accountId: String) {
    Text("Vico chart tähän $accountId")
}