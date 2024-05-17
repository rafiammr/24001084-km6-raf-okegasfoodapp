package com.rafi.okegasfood.data.repository

import com.rafi.okegasfood.data.datasource.user.UserDataSource
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.verify
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class UserModeRepositoryImplTest {
    @MockK
    lateinit var dataSource: UserDataSource

    private lateinit var repository: UserModeRepository

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        repository = UserModeRepositoryImpl(dataSource)
    }

    @Test
    fun isUsingGridMode() {
        val mockIsUsingGridMode = true
        every { dataSource.isUsingGridMode() } returns mockIsUsingGridMode
        val result = repository.isUsingGridMode()
        verify { dataSource.isUsingGridMode() }
        assertEquals(mockIsUsingGridMode, result)
    }

    @Test
    fun setUsingGridMode() {
        val isUsingGridMode = true
        every { dataSource.setUsingGridMode(any()) } returns Unit
        repository.setUsingGridMode(isUsingGridMode)
        verify { dataSource.setUsingGridMode(any()) }
    }
}
