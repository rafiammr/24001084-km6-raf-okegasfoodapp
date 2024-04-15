package com.rafi.okegasfood.data.datasource.menu

import com.rafi.okegasfood.data.source.network.model.MenuResponse

interface MenuDataSource {
    suspend fun getMenu(categoryName: String? = null): MenuResponse
}
