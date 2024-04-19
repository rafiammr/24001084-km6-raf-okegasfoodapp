package com.rafi.okegasfood.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.rafi.okegasfood.data.model.PriceItem
import com.rafi.okegasfood.data.repository.CategoryRepository
import com.rafi.okegasfood.data.repository.MenuRepository
import com.rafi.okegasfood.data.repository.UserModeRepository
import com.rafi.okegasfood.data.repository.UserRepository
import kotlinx.coroutines.Dispatchers
import java.security.PrivateKey

class HomeViewModel(
    private val categoryRepository: CategoryRepository,
    private val menuRepository: MenuRepository,
    private val userModeRepository: UserModeRepository,
    private val userRepository: UserRepository
) : ViewModel() {

    fun getMenu(categoryName: String? = null) =
        menuRepository.getMenu(categoryName).asLiveData(Dispatchers.IO)

    fun getCategories() = categoryRepository.getCategories().asLiveData(Dispatchers.IO)

    fun isUsingGridMode() = userModeRepository.isUsingGridMode()

    fun changeListGridMode() {
        val currentMode = isUsingGridMode()
        userModeRepository.setUsingGridMode(!currentMode)
    }

    fun getCurrentUser() = userRepository.getCurrentUser()
}