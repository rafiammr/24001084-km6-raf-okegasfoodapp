package com.rafi.okegasfood.data.datasource.category

import com.rafi.okegasfood.data.source.network.model.category.CategoriesResponse

interface CategoryDataSource {
    suspend fun getCategories(): CategoriesResponse
}
