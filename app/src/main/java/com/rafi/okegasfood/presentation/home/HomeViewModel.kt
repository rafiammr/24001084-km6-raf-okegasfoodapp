package com.rafi.okegasfood.presentation.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.rafi.okegasfood.data.repository.CategoryRepository
import com.rafi.okegasfood.data.repository.MenuRepository

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

    fun setGridMode(value : Boolean){
        _isUsingGridMode.postValue(value)
    }

    fun getMenu() = menuRepository.getMenu()

    fun getCategories() = categoryRepository.getCategories()

}