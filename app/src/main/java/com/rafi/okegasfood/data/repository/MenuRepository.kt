package com.rafi.okegasfood.data.repository

import com.rafi.okegasfood.data.datasource.menu.MenuDataSource
import com.rafi.okegasfood.data.mapper.toMenu
import com.rafi.okegasfood.data.model.Menu
import com.rafi.okegasfood.utils.ResultWrapper
import com.rafi.okegasfood.utils.proceedFlow
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.onStart

interface MenuRepository {
    fun getMenu(categoryName: String? = null): Flow<ResultWrapper<List<Menu>>>
}

class MenuRepositoryImpl(private val dataSource: MenuDataSource) : MenuRepository {
    override fun getMenu(categoryName: String?): Flow<ResultWrapper<List<Menu>>> {
        return proceedFlow { dataSource.getMenu(categoryName).data.toMenu() }
            .onStart {
                emit(ResultWrapper.Loading())
                delay(1000)
            }
    }


}