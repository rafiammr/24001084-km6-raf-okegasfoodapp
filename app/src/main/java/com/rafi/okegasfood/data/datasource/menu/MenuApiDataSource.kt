package com.rafi.okegasfood.data.datasource.menu

import com.rafi.okegasfood.data.source.network.model.checkout.CheckoutRequestPayload
import com.rafi.okegasfood.data.source.network.model.checkout.CheckoutResponse
import com.rafi.okegasfood.data.source.network.model.menu.MenuResponse
import com.rafi.okegasfood.data.source.network.services.OkeGasFoodApiService

class MenuApiDataSource(private val service: OkeGasFoodApiService) : MenuDataSource {
    override suspend fun getMenu(categoryName: String?): MenuResponse {
        return service.getMenu(categoryName)
    }

    override suspend fun createOrder(payload: CheckoutRequestPayload): CheckoutResponse {
        return service.createOrder(payload)
    }
}
