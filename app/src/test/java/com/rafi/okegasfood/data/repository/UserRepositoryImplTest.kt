package com.rafi.okegasfood.data.repository

import app.cash.turbine.test
import com.rafi.okegasfood.data.datasource.AuthDataSource
import com.rafi.okegasfood.utils.ResultWrapper
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.delay
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class UserRepositoryImplTest {
    @MockK
    lateinit var dataSource: AuthDataSource

    private lateinit var userRepository: UserRepository

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        userRepository = UserRepositoryImpl(dataSource)
    }

    @Test
    fun doLogin() {
        val email = "example@gmail.com"
        val password = "password"
        val mockLogin = true
        coEvery { dataSource.doLogin(email, password) } returns mockLogin

        runTest {
            userRepository.doLogin(email, password).test {
                delay(100)
                val actualData = expectMostRecentItem()
                assertTrue(actualData is ResultWrapper.Success)
            }
        }
    }

    @Test
    fun doRegister() {
        val email = "example@gmail.com"
        val fullname = "asdsdasda"
        val password = "password"
        val mockLogin = true
        coEvery { dataSource.doRegister(email, fullname, password) } returns mockLogin
        runTest {
            userRepository.doRegister(email, fullname, password).test {
                delay(100)
                val actualData = expectMostRecentItem()
                assertTrue(actualData is ResultWrapper.Success)
            }
        }
    }

    @Test
    fun updateProfile() {
        coEvery { dataSource.updateProfile(any()) } returns true
        runTest {
            userRepository.updateProfile().test {
                delay(100)
                val actualData = expectMostRecentItem()
                assertTrue(actualData is ResultWrapper.Success)
            }
        }
    }

    @Test
    fun updatePassword() {
        coEvery { dataSource.updatePassword(any()) } returns true
        runTest {
            userRepository.updatePassword("password").test {
                delay(100)
                val actualData = expectMostRecentItem()
                assertTrue(actualData is ResultWrapper.Success)
            }
        }
    }

    @Test
    fun updateEmail() {
        coEvery { dataSource.updateEmail(any()) } returns true
        runTest {
            userRepository.updateEmail("email@gmail.com").test {
                delay(100)
                val actualData = expectMostRecentItem()
                assertTrue(actualData is ResultWrapper.Success)
            }
        }
    }

    @Test
    fun requestChangePasswordByEmail() {
        runTest {
            coEvery { dataSource.requestChangePasswordByEmail() } returns true
            val result = userRepository.requestChangePasswordByEmail()
            assertTrue(result)
            coVerify { dataSource.requestChangePasswordByEmail() }
        }
    }

    @Test
    fun doLogout() {
        runTest {
            coEvery { dataSource.doLogout() } returns true
            val result = userRepository.doLogout()
            assertTrue(result)
            coVerify { dataSource.doLogout() }
        }
    }

    @Test
    fun isLoggedIn() {
        runTest {
            coEvery { dataSource.isLoggedIn() } returns true
            val result = userRepository.isLoggedIn()
            assertTrue(result)
            coVerify { dataSource.isLoggedIn() }
        }
    }

    @Test
    fun getCurrentUser() {
    }
}
