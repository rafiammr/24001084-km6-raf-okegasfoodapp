package com.rafi.okegasfood.data.model

data class Cart(
    var id: Int? = null,
    var menuId: String? = null,
    var menuName: String? = null,
    var menuImgUrl: String,
    var menuPrice: Double,
    var itemQuantity: Int = 1,
    var itemNotes: String? = null
)
