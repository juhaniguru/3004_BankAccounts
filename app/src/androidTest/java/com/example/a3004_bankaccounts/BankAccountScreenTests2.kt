package com.example.a3004_bankaccounts

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.v2.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.a3004_bankaccounts.presentation.AccountsState
import com.example.a3004_bankaccounts.presentation.BankAccountScreen
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class BankAccountScreenTests2 {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun testBankAccountScreenLoading() {
        composeTestRule.setContent {
            BankAccountScreen(
                state = AccountsState(loading = true),

                onNavigate = {}
            )
        }

        // meillä ei ole nodea nimeltä loading käyttöliittymässä
        // vielä, mutta lisätään se
        composeTestRule.onNodeWithTag("loading").assertIsDisplayed()
    }
}