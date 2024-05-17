package com.rafi.okegasfood.data.repository

import app.cash.turbine.test
import com.rafi.okegasfood.data.datasource.category.CategoryDataSource
import com.rafi.okegasfood.data.source.network.model.category.CategoriesResponse
import com.rafi.okegasfood.data.source.network.model.category.CategoryItemResponse
import com.rafi.okegasfood.utils.ResultWrapper
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class CategoryRepositoryImplTest {
    @MockK
    lateinit var dataSource: CategoryDataSource

    private lateinit var repository: CategoryRepository

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        repository = CategoryRepositoryImpl(dataSource)
    }

    @Test
    fun `get categories loading`() {
        val category1 =
            CategoryItemResponse(
                imgUrl = "awdasdaw",
                nameCategory = "awdasda",
            )
        val category2 =
            CategoryItemResponse(
                imgUrl = "awdasdaw",
                nameCategory = "awads",
            )
        val mockListCategory = listOf(category1, category2)
        val mockResponse = mockk<CategoriesResponse>()
        every { mockResponse.data } returns mockListCategory
        runTest {
            coEvery { dataSource.getCategories() } returns mockResponse
            repository.getCategories().map {
                delay(100)
                it
            }.test {
                delay(1210)
                val data = expectMostRecentItem()
                assertTrue(data is ResultWrapper.Loading)
                coVerify { dataSource.getCategories() }
            }
        }
    }

    @Test
    fun `get categories success`() {
        val category1 = CategoryItemResponse(imgUrl = "awdasdaw", nameCategory = "Es Teh")
        val category2 = CategoryItemResponse(imgUrl = "awdasdaw", nameCategory = "Es Kelapa")
        val mockListCategory = listOf(category1, category2)
        val mockResponse = mockk<CategoriesResponse>()
        every { mockResponse.data } returns mockListCategory
        runTest {
            coEvery { dataSource.getCategories() } returns mockResponse
            repository.getCategories().map {
                delay(100)
                it
            }.test {
                delay(1310)
                val data = expectMostRecentItem()
                assertTrue(data is ResultWrapper.Success)
                coVerify { dataSource.getCategories() }
            }
        }
    }

    @Test
    fun `get categories error`() {
        runTest {
            coEvery { dataSource.getCategories() } throws IllegalStateException("Mock Error")
            repository.getCategories().map {
                delay(100)
                it
            }.test {
                delay(1310)
                val data = expectMostRecentItem()
                assertTrue(data is ResultWrapper.Error)
                coVerify { dataSource.getCategories() }
            }
        }
    }

    @Test
    fun `get categories empty`() {
        val mockListCategory = listOf<CategoryItemResponse>()
        val mockResponse = mockk<CategoriesResponse>()
        every { mockResponse.data } returns mockListCategory
        runTest {
            coEvery { dataSource.getCategories() } returns mockResponse
            repository.getCategories().map {
                delay(100)
                it
            }.test {
                delay(1310)
                val data = expectMostRecentItem()
                assertTrue(data is ResultWrapper.Empty)
                coVerify { dataSource.getCategories() }
            }
        }
    }
}
