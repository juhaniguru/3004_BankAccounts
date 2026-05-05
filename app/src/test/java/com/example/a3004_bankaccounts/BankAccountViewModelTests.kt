package com.example.a3004_bankaccounts

import com.example.a3004_bankaccounts.domain.BankAccount
import com.example.a3004_bankaccounts.domain.BankAccountAPI
import com.example.a3004_bankaccounts.domain.DetailDataPoint
import com.example.a3004_bankaccounts.presentation.BankAccountViewModel
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test

class MockAPI(private val result: List<BankAccount> = emptyList(), private val err: String? = null) : BankAccountAPI {
    override suspend fun getAccounts(): List<BankAccount> {
        if(err != null) {
            throw Exception(err)
        }

        return result
    }

    override suspend fun getAccountDetails(
        accountId: Int,
        dt: Long?,
        step: String
    ): List<DetailDataPoint> {
        TODO("Not yet implemented")
    }


}

class BankAccountViewModelTests {
    //AAA

    private lateinit var vm: BankAccountViewModel
    private lateinit var api: BankAccountAPI

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setup() {


        Dispatchers.setMain(Dispatchers.Unconfined)


    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @After
    fun tearDown() {

        Dispatchers.resetMain()

    }

    @Test
    fun testGetAccountsErr()  {

        // ARRANGE
        api = MockAPI(
            err = "Permission denied"
        )
        vm = BankAccountViewModel(api = api)

        // ACT

        vm.getAccounts()

        // ASSERT
        assertEquals(emptyList<BankAccount>(), vm.accountsState.value.items)
        assertEquals("Permission denied", vm.accountsState.value.err)

    }

    @Test
    fun testGetAccountsOkWithEmptyList() {

        // ARRANGE
        api = MockAPI()
        vm = BankAccountViewModel(api = api)

        // ACT

        val accounts = vm.getAccounts()

        // ASSERT
        assertEquals(emptyList<BankAccount>(), vm.accountsState.value.items)



    }

    @Test
    fun testGetAccountsOkWithData() {

        val expectedResult = listOf(
            BankAccount(1, "juhanintili")
        )

        // ARRANGE
        api = MockAPI(
            result = expectedResult

        )
        vm = BankAccountViewModel(api = api)

        // ACT

        val accounts = vm.getAccounts()

        // ASSERT
        assertEquals(expectedResult, vm.accountsState.value.items)



    }

}