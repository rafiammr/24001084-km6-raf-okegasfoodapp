package com.rafi.okegasfood.presentation.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.rafi.okegasfood.data.datasource.category.CategoryDataSource
import com.rafi.okegasfood.data.datasource.category.DummyCategoryDataSource
import com.rafi.okegasfood.data.datasource.menu.MenuDataSource
import com.rafi.okegasfood.data.datasource.menu.DummyMenuDataSource
import com.rafi.okegasfood.data.datasource.repository.CategoryRepository
import com.rafi.okegasfood.data.datasource.repository.MenuRepository

class HomeViewModel(
    private val categoryRepository: CategoryRepository,
    private val menuRepository: MenuRepository
    ) : ViewModel() {
    private val _isUsingGridMode = MutableLiveData(false)
    val isUsingGridMode : LiveData<Boolean>
        get() = _isUsingGridMode

    fun changeListMode(){
        val currentValue = _isUsingGridMode.value ?: false
        _isUsingGridMode.postValue(!currentValue)
    }

    fun getMenu() = menuRepository.getMenu()

    fun getCatigories() = categoryRepository.getCategories()

}