package com.rafi.okegasfood.presentation.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.rafi.okegasfood.data.datasource.CategoryDataSource
import com.rafi.okegasfood.data.datasource.CategoryDataSourceImpl
import com.rafi.okegasfood.data.datasource.MenuDataSource
import com.rafi.okegasfood.data.datasource.MenuDataSourceImpl

class HomeViewModel : ViewModel() {
    private val dataSourceMenu: MenuDataSource by lazy { MenuDataSourceImpl() }
    private val dataSourceCategory: CategoryDataSource by lazy { CategoryDataSourceImpl() }
    private val _isUsingGridMode = MutableLiveData(false)
    val isUsingGridMode : LiveData<Boolean>
        get() = _isUsingGridMode

    fun changeListMode(){
        val currentValue = _isUsingGridMode.value ?: false
        _isUsingGridMode.postValue(!currentValue)
    }

    fun getCategoryList() = dataSourceCategory.getCategoryData()
    fun getMenuList() = dataSourceMenu.getMenuData()
}