package com.rafi.okegasfood.data.repository

import com.rafi.okegasfood.data.datasource.menu.MenuDataSource
import com.rafi.okegasfood.data.mapper.toMenu
import com.rafi.okegasfood.data.model.Cart
import com.rafi.okegasfood.data.model.Menu
import com.rafi.okegasfood.data.source.network.model.checkout.CheckoutItemPayload
import com.rafi.okegasfood.data.source.network.model.checkout.CheckoutRequestPayload
import com.rafi.okegasfood.utils.ResultWrapper
import com.rafi.okegasfood.utils.proceedFlow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.onStart

interface MenuRepository {
    fun getMenu(categoryName: String? = null): Flow<ResultWrapper<List<Menu>>>

    fun createOrder(menu: List<Cart>): Flow<ResultWrapper<Boolean>>
}

class MenuRepositoryImpl(private val dataSource: MenuDataSource) : MenuRepository {
    override fun getMenu(categoryName: String?): Flow<ResultWrapper<List<Menu>>> {
        return proceedFlow { dataSource.getMenu(categoryName).data.toMenu() }
            .onStart {
                emit(ResultWrapper.Loading())
            }
    }

    override fun createOrder(menu: List<Cart>): Flow<ResultWrapper<Boolean>> {
        return proceedFlow {
            dataSource.createOrder(CheckoutRequestPayload(
                orders = menu.map {
                    CheckoutItemPayload(
                        name = it.menuName,
                        quantity = it.itemQuantity,
                        notes = it.itemNotes.orEmpty(),
                        price = it.menuPrice
                    )
                }
            )).status ?: false
        }
    }


}