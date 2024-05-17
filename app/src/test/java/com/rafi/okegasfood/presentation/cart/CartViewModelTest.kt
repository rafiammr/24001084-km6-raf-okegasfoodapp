package com.rafi.okegasfood.presentation.cart

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.rafi.okegasfood.data.repository.CartRepository
import com.rafi.okegasfood.data.repository.UserRepository
import com.rafi.okegasfood.tools.MainCoroutineRule
import com.rafi.okegasfood.tools.getOrAwaitValue
import com.rafi.okegasfood.utils.ResultWrapper
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import io.mockk.spyk
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule

class CartViewModelTest {
    @get:Rule
    val testRule: TestRule = InstantTaskExecutorRule()

    @OptIn(ExperimentalCoroutinesApi::class)
    @get:Rule
    val coroutineRule: TestRule = MainCoroutineRule(UnconfinedTestDispatcher())

    @MockK
    lateinit var repoCart: CartRepository

    @MockK
    lateinit var repoUser: UserRepository

    lateinit var viewModel: CartViewModel

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        viewModel = spyk(CartViewModel(repoCart, repoUser))
    }

    @Test
    fun getAllCarts_success() {
        every { repoCart.getUserCartData() } returns
            flow {
                emit(
                    ResultWrapper.Success(
                        Pair(listOf(mockk(relaxed = true), mockk(relaxed = true)), 8000.0),
                    ),
                )
            }
        val result = viewModel.getAllCarts().getOrAwaitValue()
        assertEquals(2, result.payload?.first?.size)
        assertEquals(8000.0, result.payload?.second)
    }

    @Test
    fun getAllCarts_loading() {
        every { repoCart.getUserCartData() } returns
            flow {
                emit(
                    ResultWrapper.Loading(
                        Pair(listOf(mockk(relaxed = true), mockk(relaxed = true)), 8000.0),
                    ),
                )
            }
        val result = viewModel.getAllCarts().getOrAwaitValue()
        assertEquals(2, result.payload?.first?.size)
        assertEquals(8000.0, result.payload?.second)
    }

    @Test
    fun decreaseCart() {
        every { repoCart.decreaseCart(any()) } returns
            flow {
                emit(ResultWrapper.Success(true))
            }
        viewModel.decreaseCart(mockk())
        verify { repoCart.decreaseCart(any()) }
    }

    @Test
    fun increaseCart() {
        every { repoCart.increaseCart(any()) } returns
            flow {
                emit(ResultWrapper.Success(true))
            }
        viewModel.increaseCart(mockk())
        verify { repoCart.increaseCart(any()) }
    }

    @Test
    fun removeCart() {
        every { repoCart.deleteCart(any()) } returns
            flow {
                emit(ResultWrapper.Success(true))
            }
        viewModel.removeCart(mockk())
        verify { repoCart.deleteCart(any()) }
    }

    @Test
    fun setCartNotes() {
        every { repoCart.setCartNotes(any()) } returns
            flow {
                emit(ResultWrapper.Success(true))
            }
        viewModel.setCartNotes(mockk())
        verify { repoCart.setCartNotes(any()) }
    }

    @Test
    fun isUserLoggedIn() {
        every { repoUser.isLoggedIn() } returns true
        val result = viewModel.isUserLoggedIn()
        assertTrue(result)
    }
}
