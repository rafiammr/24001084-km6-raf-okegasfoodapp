package com.rafi.okegasfood.data.datasource.menu

import com.rafi.okegasfood.data.source.network.model.menu.MenuResponse
import com.rafi.okegasfood.data.source.network.model.checkout.CheckoutRequestPayload
import com.rafi.okegasfood.data.source.network.model.checkout.CheckoutResponse

interface MenuDataSource {
    suspend fun getMenu(categoryName: String? = null): MenuResponse

    suspend fun createOrder(payload: CheckoutRequestPayload): CheckoutResponse
}
