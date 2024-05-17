package com.rafi.okegasfood.presentation.login

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.rafi.okegasfood.data.repository.UserRepository
import com.rafi.okegasfood.tools.MainCoroutineRule
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

class LoginViewModelTest {
    @get:Rule
    val testRule: TestRule = InstantTaskExecutorRule()

    @OptIn(ExperimentalCoroutinesApi::class)
    @get:Rule
    val coroutineRule: TestRule = MainCoroutineRule(UnconfinedTestDispatcher())

    @MockK
    lateinit var userRepository: UserRepository

    private lateinit var viewModel: LoginViewModel

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        viewModel = LoginViewModel(userRepository)
    }

    @Test
    fun doLogin() {
        val email = "example@gmail.com"
        val password = "password"

        coEvery { userRepository.doLogin(email, password) } returns
            flow {
                emit(ResultWrapper.Success(true))
            }

        var result: ResultWrapper<Boolean>? = null

        val observer =
            Observer<ResultWrapper<Boolean>> {
                result = it
            }

        viewModel.loginResult.observeForever(observer)

        viewModel.doLogin(email, password)
        assertEquals(true, result)
    }
}
