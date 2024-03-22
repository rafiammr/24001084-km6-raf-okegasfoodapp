package com.rafi.okegasfood.data.model

import androidx.annotation.DrawableRes
import java.util.UUID

data class Menu(
    var id: String = UUID.randomUUID().toString(),
    var imgUrl: String,
    var price: Double,
    var name: String,
    var shortDesc: String,
    var location: String,
    var urlLocation: String,
)
