package com.example.a3004_bankaccounts.presentation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel


@Composable
fun BankAccountScreenRoot(modifier: Modifier = Modifier) {
    val vm = viewModel<BankAccountViewModel>(factory = BankAccountViewModel.createFactory())
}