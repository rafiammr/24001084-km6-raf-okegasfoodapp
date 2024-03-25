package com.rafi.okegasfood.data.datasource.menu

import com.rafi.okegasfood.data.model.Menu

interface MenuDataSource {
    fun getMenu(): List<Menu>
}
