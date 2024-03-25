package com.rafi.okegasfood.data.datasource.category

import com.rafi.okegasfood.data.model.Category

interface CategoryDataSource {
    fun getCategories(): List<Category>
}