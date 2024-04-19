package com.rafi.okegasfood.data.mapper

import com.rafi.okegasfood.data.model.Category
import com.rafi.okegasfood.data.source.network.model.category.CategoryItemResponse

fun CategoryItemResponse?.toCategory() = Category(
    imgUrl = this?.imgUrl.orEmpty(),
    nameCategory = this?.nameCategory.orEmpty(),
)

fun Collection<CategoryItemResponse>.toCategories() = this.map {
    it.toCategory()
}