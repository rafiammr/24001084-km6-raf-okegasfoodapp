package com.rafi.okegasfood.data.source.network.model.menu

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class MenuItemResponse(
    @SerializedName("image_url")
    val imgUrl: String?,
    @SerializedName("nama")
    val nameMenu: String?,
    @SerializedName("harga_format")
    val priceFormat: String?,
    @SerializedName("harga")
    val price: Double?,
    @SerializedName("detail")
    val detail: String?,
    @SerializedName("alamat_resto")
    val location: String?
)