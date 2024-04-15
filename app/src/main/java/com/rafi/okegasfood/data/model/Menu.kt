package com.rafi.okegasfood.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.util.UUID

@Parcelize
data class Menu(
    var id: String = UUID.randomUUID().toString(),
    var imgUrl: String,
    var nameMenu: String,
    val priceFormat : String,
    var price: Double,
    var detail: String,
    var location: String,
    var urlLocation: String
) : Parcelable
