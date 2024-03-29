package com.rafi.okegasfood.data.repository

import com.rafi.okegasfood.data.datasource.category.CategoryDataSource
import com.rafi.okegasfood.data.model.Category

interface CategoryRepository {
    fun getCategories(): List<Category>
}

class CategoryRepositoryImpl(private val dataSource: CategoryDataSource) : CategoryRepository {
    override fun getCategories(): List<Category> = dataSource.getCategories()

}