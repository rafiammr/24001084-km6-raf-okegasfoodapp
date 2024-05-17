package com.rafi.okegasfood.data.datasource.menu

import com.rafi.okegasfood.data.source.network.model.checkout.CheckoutRequestPayload
import com.rafi.okegasfood.data.source.network.model.checkout.CheckoutResponse
import com.rafi.okegasfood.data.source.network.model.menu.MenuResponse
import com.rafi.okegasfood.data.source.network.services.OkeGasFoodApiService
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class MenuApiDataSourceTest {
    @MockK
    lateinit var service: OkeGasFoodApiService

    private lateinit var dataSource: MenuDataSource

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        dataSource = MenuApiDataSource(service)
    }

    @Test
    fun getMenu() {
        runTest {
            val mockResponse = mockk<MenuResponse>(relaxed = true)
            coEvery { service.getMenu(any()) } returns mockResponse
            val actualResult = dataSource.getMenu("makanan")
            coVerify { service.getMenu(any()) }
            assertEquals(mockResponse, actualResult)
        }
    }

    @Test
    fun createOrder() {
        runTest {
            val mockResponse = mockk<CheckoutResponse>(relaxed = true)
            val mockRequest = mockk<CheckoutRequestPayload>()
            coEvery { service.createOrder(any()) } returns mockResponse
            val actualResult = dataSource.createOrder(mockRequest)
            coVerify { service.createOrder(any()) }
            assertEquals(mockResponse, actualResult)
        }
    }
}
