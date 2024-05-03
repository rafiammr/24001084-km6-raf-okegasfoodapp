package com.rafi.okegasfood.data.source.network.model.checkout

import com.google.gson.annotations.SerializedName

data class CheckoutRequestPayload(
    @SerializedName("username")
    var username: String,
    @SerializedName("total")
    var total: Int,
    @SerializedName("orders")
    var orders: List<CheckoutItemPayload>,
)
