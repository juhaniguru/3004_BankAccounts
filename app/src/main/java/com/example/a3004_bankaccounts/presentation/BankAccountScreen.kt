package com.example.a3004_bankaccounts.presentation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.a3004_bankaccounts.R
import com.example.a3004_bankaccounts.domain.BankAccount


@Composable
fun BankAccountScreenRoot(modifier: Modifier = Modifier, onNavigate: (Int) -> Unit) {
    val vm = viewModel<BankAccountViewModel>(factory = BankAccountViewModel.createFactory())
    val state by vm.accountsState.collectAsStateWithLifecycle()

    BankAccountScreen(state = state, onNavigate = onNavigate)


}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BankAccountScreen(modifier: Modifier = Modifier, state: AccountsState, onNavigate: (Int) -> Unit) {

    Scaffold(topBar = {
        TopAppBar(title = {
            Text(stringResource(R.string.bank_accounts))
        })
    }) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues), contentAlignment = Alignment.Center
        ) {
            when {
                state.loading -> CircularProgressIndicator()
                state.err != null -> Text(state.err)
                else -> AccountsList(accounts = state.items, onNavigate = onNavigate)
            }
        }
    }
}

@Composable
fun AccountsList(modifier: Modifier = Modifier, accounts: List<BankAccount>, onNavigate : (Int) -> Unit) {
    LazyColumn(modifier = Modifier.fillMaxSize()) {
        items(items = accounts) { bankAccount ->
            AccountItem(account = bankAccount, onNavigate = onNavigate)
        }
    }
}

@Composable
fun AccountItem(modifier: Modifier = Modifier, account: BankAccount, onNavigate: (Int) -> Unit) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .clickable {
                onNavigate(account.id)
            },


        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Surface(
                shape = CircleShape,
                color = MaterialTheme.colorScheme.primaryContainer,
                modifier = Modifier.size(40.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.AccountCircle,
                    contentDescription = "",
                    modifier = Modifier.padding(8.dp),
                    tint = MaterialTheme.colorScheme.onPrimaryContainer
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            Column {
                Text(
                    text = stringResource(R.string.account_number),
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.secondary
                )
                Text(
                    text = account.accountNumber,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}