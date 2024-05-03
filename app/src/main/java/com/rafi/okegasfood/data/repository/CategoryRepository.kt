package com.rafi.okegasfood.data.repository

import com.rafi.okegasfood.data.datasource.category.CategoryDataSource
import com.rafi.okegasfood.data.mapper.toCategories
import com.rafi.okegasfood.data.model.Category
import com.rafi.okegasfood.utils.ResultWrapper
import com.rafi.okegasfood.utils.proceedFlow
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.onStart

interface CategoryRepository {
    fun getCategories(): Flow<ResultWrapper<List<Category>>>
}

class CategoryRepositoryImpl(private val dataSource: CategoryDataSource) : CategoryRepository {
    override fun getCategories(): Flow<ResultWrapper<List<Category>>> {
        return proceedFlow { dataSource.getCategories().data.toCategories() }
            .onStart {
                emit(ResultWrapper.Loading())
                delay(1000)
            }
    }
}
