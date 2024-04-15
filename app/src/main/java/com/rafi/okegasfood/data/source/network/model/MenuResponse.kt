package com.rafi.okegasfood.data.source.network.model

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class MenuResponse(
    @SerializedName("status")
    val status: Boolean?,
    @SerializedName("code")
    val code: Int?,
    @SerializedName("message")
    val message: String?,
    @SerializedName("data")
    val data: List<MenuItemResponse>?
)