package com.rafi.okegasfood.data.datasource.repository

import com.rafi.okegasfood.data.datasource.menu.MenuDataSource
import com.rafi.okegasfood.data.model.Menu


interface MenuRepository {
    fun getMenu() : List<Menu>
}

class MenuRepositoryImpl(private val dataSource: MenuDataSource) : MenuRepository{
    override fun getMenu(): List<Menu> = dataSource.getMenu()

}