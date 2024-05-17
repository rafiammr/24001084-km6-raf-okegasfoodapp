package com.rafi.okegasfood.presentation.register

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.rafi.okegasfood.data.repository.UserRepository
import com.rafi.okegasfood.tools.MainCoroutineRule
import com.rafi.okegasfood.tools.getOrAwaitValue
import com.rafi.okegasfood.utils.ResultWrapper
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule

class RegisterViewModelTest {
    @get:Rule
    val testRule: TestRule = InstantTaskExecutorRule()

    @OptIn(ExperimentalCoroutinesApi::class)
    @get:Rule
    val coroutineRule: TestRule = MainCoroutineRule(UnconfinedTestDispatcher())

    @MockK
    lateinit var userRepository: UserRepository

    private lateinit var viewModel: RegisterViewModel

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        viewModel = RegisterViewModel(userRepository)
    }

    @Test
    fun doRegister() {
        val email = "example@gmail.com"
        val fullName = "adsadsad"
        val password = "password"

        coEvery { userRepository.doRegister(email, fullName, password) } returns
            flow {
                emit(ResultWrapper.Success(true))
            }

        val result = viewModel.doRegister(email, fullName, password).getOrAwaitValue()

        assertTrue(result is ResultWrapper.Success)
        assertEquals(true, result.payload)
    }
}
