package com.rafi.okegasfood.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.rafi.okegasfood.data.repository.CategoryRepository
import com.rafi.okegasfood.data.repository.MenuRepository
import com.rafi.okegasfood.data.repository.UserModeRepository
import kotlinx.coroutines.Dispatchers

class HomeViewModel(
    private val categoryRepository: CategoryRepository,
    private val menuRepository: MenuRepository,
    private val userModeRepository: UserModeRepository
) : ViewModel() {

    fun getMenu(categoryName: String? = null) =
        menuRepository.getMenu(categoryName).asLiveData(Dispatchers.IO)

    fun getCategories() = categoryRepository.getCategories().asLiveData(Dispatchers.IO)

    fun isUsingGridMode() = userModeRepository.isUsingGridMode()

    fun changeListGridMode() {
        val currentMode = isUsingGridMode()
        userModeRepository.setUsingGridMode(!currentMode)
    }
}