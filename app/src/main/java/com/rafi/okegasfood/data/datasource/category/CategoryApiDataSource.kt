package com.rafi.okegasfood.data.datasource.category

import com.rafi.okegasfood.data.source.network.model.category.CategoriesResponse
import com.rafi.okegasfood.data.source.network.services.OkeGasFoodApiService

class CategoryApiDataSource(private val service: OkeGasFoodApiService) : CategoryDataSource {
    override suspend fun getCategories(): CategoriesResponse {
        return service.getCategories()
    }

}