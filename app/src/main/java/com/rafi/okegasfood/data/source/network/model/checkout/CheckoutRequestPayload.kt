package com.rafi.okegasfood.data.source.network.model.checkout

import com.google.gson.annotations.SerializedName

data class CheckoutRequestPayload(
    @SerializedName("orders")
    var orders : List<CheckoutItemPayload>
)