package com.rafi.okegasfood.presentation.checkout

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.firebase.auth.FirebaseUser
import com.rafi.okegasfood.data.model.PriceItem
import com.rafi.okegasfood.data.repository.CartRepository
import com.rafi.okegasfood.data.repository.MenuRepository
import com.rafi.okegasfood.data.source.firebase.FirebaseService
import com.rafi.okegasfood.tools.MainCoroutineRule
import com.rafi.okegasfood.utils.ResultWrapper
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule

class CheckoutViewModelTest {
    @get:Rule
    val testRule: TestRule = InstantTaskExecutorRule()

    @OptIn(ExperimentalCoroutinesApi::class)
    @get:Rule
    val coroutineRule: TestRule = MainCoroutineRule(UnconfinedTestDispatcher())

    @MockK
    lateinit var cartRepository: CartRepository

    @MockK
    lateinit var menuRepository: MenuRepository

    @MockK
    lateinit var firebaseService: FirebaseService

    private lateinit var viewModel: CheckoutViewModel

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        every { cartRepository.getCheckoutData() } returns
            flow {
                emit(
                    ResultWrapper.Success(
                        Triple(
                            listOf(mockk(relaxed = true), mockk(relaxed = true)),
                            listOf(
                                PriceItem("aasasaaa", 8000.0),
                                PriceItem("aasasaaa", 8000.0),
                            ),
                            16000.0,
                        ),
                    ),
                )
            }
        viewModel = CheckoutViewModel(cartRepository, menuRepository, firebaseService)
    }

    @Test
    fun checkoutCart() {
        coEvery { cartRepository.getCheckoutData() } returns
            flow {
                emit(
                    ResultWrapper.Success(
                        Triple(
                            listOf(mockk(relaxed = true), mockk(relaxed = true)),
                            listOf(
                                PriceItem("aasasaaa", 8000.0),
                                PriceItem("aasasaaa", 8000.0),
                            ),
                            16000.0,
                        ),
                    ),
                )
            }
        coEvery {
            menuRepository.createOrder(any())
        } returns
            flow {
                emit(ResultWrapper.Success(true))
            }
        viewModel.checkoutCart()
    }

    @Test
    fun getCurrentUser() {
        val currentUser = mockk<FirebaseUser>()
        every { firebaseService.getCurrentUser() } returns currentUser
        val result = viewModel.getCurrentUser()
        assertEquals(currentUser, result)
    }

    @Test
    fun deleteAllCart() {
        viewModel.deleteAllCart()
        coEvery { cartRepository.deleteAllCart() }
    }
}
