package com.rafi.okegasfood.presentation.home

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.rafi.okegasfood.data.model.User
import com.rafi.okegasfood.data.repository.CategoryRepository
import com.rafi.okegasfood.data.repository.MenuRepository
import com.rafi.okegasfood.data.repository.UserModeRepository
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

class HomeViewModelTest {
    @get:Rule
    val testRule: TestRule = InstantTaskExecutorRule()

    @OptIn(ExperimentalCoroutinesApi::class)
    @get:Rule
    val coroutineRule: TestRule = MainCoroutineRule(UnconfinedTestDispatcher())

    @MockK
    lateinit var categoryRepository: CategoryRepository

    @MockK
    lateinit var menuRepository: MenuRepository

    @MockK
    lateinit var userModeRepository: UserModeRepository

    @MockK
    lateinit var userRepository: UserRepository

    private lateinit var viewModel: HomeViewModel

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        viewModel =
            spyk(
                HomeViewModel(
                    categoryRepository,
                    menuRepository,
                    userModeRepository,
                    userRepository,
                ),
            )
    }

    @Test
    fun getMenu() {
        every { menuRepository.getMenu() } returns
            flow {
                emit(ResultWrapper.Success(listOf(mockk(), mockk())))
            }
        every { menuRepository.getMenu(any()) } returns
            flow {
                emit(ResultWrapper.Success(listOf(mockk(), mockk())))
            }
        val result = viewModel.getMenu().getOrAwaitValue()
        assertEquals(2, result.payload?.size)
        verify { menuRepository.getMenu(any()) }
    }

    @Test
    fun getCategories() {
        every { categoryRepository.getCategories() } returns
            flow {
                emit(ResultWrapper.Success(listOf(mockk(), mockk())))
            }
        val result = viewModel.getCategories().getOrAwaitValue()
        assertEquals(2, result.payload?.size)
        verify { categoryRepository.getCategories() }
    }

    @Test
    fun isUsingGridMode() {
        every { userModeRepository.isUsingGridMode() } returns true
        val result = viewModel.isUsingGridMode()
        assertEquals(true, result)
        verify { userModeRepository.isUsingGridMode() }
    }

    /*@Test
    fun changeListGridMode() {
        every { userModeRepository.isUsingGridMode() } returns true
        viewModel.changeListGridMode()
        verify { userModeRepository.setUsingGridMode(false) }
    }*/

    @Test
    fun getCurrentUser() {
        val currentUser = mockk<User>()
        every { userRepository.getCurrentUser() } returns currentUser
        val result = viewModel.getCurrentUser()
        assertEquals(currentUser, result)
    }
}
