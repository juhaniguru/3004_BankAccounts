package com.example.a3004_bankaccounts

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.a3004_bankaccounts.presentation.BankAccountDetailsScreenRoot
import com.example.a3004_bankaccounts.presentation.BankAccountScreenRoot
import com.example.a3004_bankaccounts.ui.theme._3004_BankAccountsTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            _3004_BankAccountsTheme {
                val navController = rememberNavController()
                NavHost(navController = navController, startDestination = "accountsScreen") {
                    composable(route="accountsScreen") {
                        BankAccountScreenRoot(onNavigate = {accountId ->
                            navController.navigate("bankAccountDetailsScreen/$accountId")

                        })
                    }

                    composable(route = "bankAccountDetailsScreen/{accountId}") {
                        BankAccountDetailsScreenRoot()
                    }
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    _3004_BankAccountsTheme {
        Greeting("Android")
    }
}