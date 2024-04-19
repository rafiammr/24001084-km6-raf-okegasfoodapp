package com.rafi.okegasfood.data.mapper

import com.rafi.okegasfood.data.model.Menu
import com.rafi.okegasfood.data.source.network.model.menu.MenuItemResponse

fun MenuItemResponse?.toMenu() = Menu(
    imgUrl = this?.imgUrl.orEmpty(),
    nameMenu = this?.nameMenu.orEmpty(),
    price = this?.price ?: 0.0,
    priceFormat = this?.priceFormat.orEmpty(),
    detail = this?.detail.orEmpty(),
    location = this?.location.orEmpty(),
    urlLocation = "https://maps.app.goo.gl/h4wQKqaBuXzftGK77"
)

fun Collection<MenuItemResponse>?.toMenu() = this?.map {
    it.toMenu()
} ?: listOf()