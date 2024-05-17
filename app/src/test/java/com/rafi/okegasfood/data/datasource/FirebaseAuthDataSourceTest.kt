package com.rafi.okegasfood.data.datasource

import com.rafi.okegasfood.data.source.firebase.FirebaseService
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class FirebaseAuthDataSourceTest {
    @MockK
    lateinit var service: FirebaseService

    private lateinit var dataSource: AuthDataSource

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        dataSource = FirebaseAuthDataSource(service)
    }

    @Test
    fun doLogin() {
        runTest {
            val mockEmail = "test@example.com"
            val mockPassword = "password"
            coEvery { service.doLogin(mockEmail, mockPassword) } returns true

            val result = dataSource.doLogin(mockEmail, mockPassword)
            assertTrue(result)
        }
    }

    @Test
    fun doRegister() {
        runTest {
            val mockEmail = "test@example.com"
            val mockFullName = "test"
            val mockPassword = "password"
            coEvery { service.doRegister(mockEmail, mockFullName, mockPassword) } returns true

            val result = dataSource.doRegister(mockEmail, mockFullName, mockPassword)
            assertTrue(result)
        }
    }

    @Test
    fun updateProfile() {
        runTest {
            val mockUpdateProfile = true
            coEvery { service.updateProfile(any()) } returns mockUpdateProfile
            val actualResult = dataSource.updateProfile()
            coVerify { service.updateProfile(any()) }
            assertEquals(mockUpdateProfile, actualResult)
        }
    }

    @Test
    fun updatePassword() {
        runTest {
            val mockUpdatePassword = true
            val mockNewPassword = "newPassword"
            coEvery { service.updatePassword(mockNewPassword) } returns mockUpdatePassword
            val actualResult = dataSource.updatePassword(mockNewPassword)
            coVerify { service.updatePassword(mockNewPassword) }
            assertEquals(mockUpdatePassword, actualResult)
        }
    }

    @Test
    fun updateEmail() {
        runTest {
            val mockUpdateEmail = true
            val mockNewEmail = "newemail@example.com"
            coEvery { service.updateEmail(mockNewEmail) } returns mockUpdateEmail
            val actualResult = dataSource.updateEmail(mockNewEmail)
            coVerify { service.updateEmail(mockNewEmail) }
            assertEquals(mockUpdateEmail, actualResult)
        }
    }

    @Test
    fun requestChangePasswordByEmail() {
        runTest {
            val mockRequestChangePassword = true
            coEvery { service.requestChangePasswordByEmail() } returns mockRequestChangePassword
            val actualResult = dataSource.requestChangePasswordByEmail()
            coVerify { service.requestChangePasswordByEmail() }
            assertEquals(mockRequestChangePassword, actualResult)
        }
    }

    @Test
    fun doLogout() {
        runTest {
            val mockLogout = true
            coEvery { service.doLogout() } returns mockLogout
            val actualResult = dataSource.doLogout()
            coVerify { service.doLogout() }
            assertEquals(mockLogout, actualResult)
        }
    }

    @Test
    fun isLoggedIn() {
        runTest {
            val mockLogged = true
            coEvery { service.isLoggedIn() } returns mockLogged
            val actualResult = dataSource.isLoggedIn()
            coVerify { service.isLoggedIn() }
            assertEquals(mockLogged, actualResult)
        }
    }

    /*@Test
    fun getCurrentUser() {
        runTest {
            val mockFirebase = mockk<FirebaseUser>(relaxed = true)
            coEvery { service.getCurrentUser() } returns mockFirebase

            val actualUser = dataSource.getCurrentUser()

            coVerify { service.getCurrentUser() }
            assertEquals(mockFirebase, actualUser)
        }
    }*/
}
