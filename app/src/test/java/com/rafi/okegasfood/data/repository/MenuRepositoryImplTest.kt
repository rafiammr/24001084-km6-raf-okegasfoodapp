package com.rafi.okegasfood.data.repository

import app.cash.turbine.test
import com.rafi.okegasfood.data.datasource.menu.MenuDataSource
import com.rafi.okegasfood.data.source.firebase.FirebaseService
import com.rafi.okegasfood.data.source.network.model.menu.MenuItemResponse
import com.rafi.okegasfood.data.source.network.model.menu.MenuResponse
import com.rafi.okegasfood.utils.ResultWrapper
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import java.lang.IllegalStateException

class MenuRepositoryImplTest {
    @MockK
    lateinit var dataSource: MenuDataSource

    @MockK
    lateinit var firebaseService: FirebaseService

    private lateinit var repo: MenuRepository

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        repo = MenuRepositoryImpl(dataSource, firebaseService)
    }

    @Test
    fun getMenu_success() {
        val menu1 =
            MenuItemResponse(
                imgUrl = "asdasdasd",
                nameMenu = "asdsdsd",
                priceFormat = "dadaddd",
                price = 10000.0,
                detail = "asdsdsd",
                location = "dsadsadsd",
            )
        val menu2 =
            MenuItemResponse(
                imgUrl = "asdasdasd",
                nameMenu = "asdsdsd",
                priceFormat = "dadaddd",
                price = 10000.0,
                detail = "asdsdsd",
                location = "dsadsadsd",
            )
        val mockListMenu = listOf(menu1, menu2)
        val mockResponse = mockk<MenuResponse>()
        coEvery { mockResponse.data } returns mockListMenu
        runTest {
            coEvery { dataSource.getMenu() } returns mockResponse
            repo.getMenu().map {
                delay(100)
                it
            }.test {
                delay(1310)
                val data = expectMostRecentItem()
                assertTrue(data is ResultWrapper.Success)
                coVerify { dataSource.getMenu() }
            }
        }
    }

    @Test
    fun getMenu_loading() {
        val menu1 =
            MenuItemResponse(
                imgUrl = "asdasdasd",
                nameMenu = "asdsdsd",
                priceFormat = "dadaddd",
                price = 10000.0,
                detail = "asdsdsd",
                location = "dsadsadsd",
            )
        val menu2 =
            MenuItemResponse(
                imgUrl = "asdasdasd",
                nameMenu = "asdsdsd",
                priceFormat = "dadaddd",
                price = 10000.0,
                detail = "asdsdsd",
                location = "dsadsadsd",
            )
        val mockListMenu = listOf(menu1, menu2)
        val mockResponse = mockk<MenuResponse>()
        coEvery { mockResponse.data } returns mockListMenu
        runTest {
            coEvery { dataSource.getMenu() } returns mockResponse
            repo.getMenu().map {
                delay(100)
                it
            }.test {
                delay(210)
                val data = expectMostRecentItem()
                assertTrue(data is ResultWrapper.Loading)
                coVerify { dataSource.getMenu() }
            }
        }
    }

    @Test
    fun getMenu_error() {
        runTest {
            coEvery { dataSource.getMenu() } throws IllegalStateException("Mock error")
            repo.getMenu().map {
                delay(100)
                it
            }.test {
                delay(1310)
                val data = expectMostRecentItem()
                assertTrue(data is ResultWrapper.Error)
                coVerify { dataSource.getMenu() }
            }
        }
    }

    @Test
    fun getMenu_empty() {
        val mockListMenu = listOf<MenuItemResponse>()
        val mockResponse = mockk<MenuResponse>()
        coEvery { mockResponse.data } returns mockListMenu
        runTest {
            coEvery { dataSource.getMenu() } returns mockResponse
            repo.getMenu().map {
                delay(100)
                it
            }.test {
                delay(1310)
                val data = expectMostRecentItem()
                assertTrue(data is ResultWrapper.Empty)
                coVerify { dataSource.getMenu() }
            }
        }
    }

    @Test
    fun createOrder() {
    }
}
