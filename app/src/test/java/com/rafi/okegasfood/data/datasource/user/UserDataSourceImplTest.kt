package com.rafi.okegasfood.data.datasource.user

import com.rafi.okegasfood.data.source.local.pref.UserPreference
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.verify
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class UserDataSourceImplTest {
    @MockK
    lateinit var userPreference: UserPreference

    private lateinit var userDataSource: UserDataSource

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        userDataSource = UserDataSourceImpl(userPreference)
    }

    @Test
    fun isUsingGridMode() {
        val mockIsUsingGridMode = true
        every { userPreference.isUsingGridMode() } returns mockIsUsingGridMode
        val result = userDataSource.isUsingGridMode()
        assertEquals(mockIsUsingGridMode, result)
    }

    @Test
    fun setUsingGridMode() {
        val isUsingGridMode = true
        every { userPreference.setUsingGridMode(any()) } returns Unit
        userDataSource.setUsingGridMode(isUsingGridMode)
        verify { userPreference.setUsingGridMode(any()) }
    }
}
