package com.rafi.okegasfood.presentation.checkout

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.rafi.okegasfood.data.repository.CartRepository
import com.rafi.okegasfood.data.repository.MenuRepository
import com.rafi.okegasfood.data.source.firebase.FirebaseService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CheckoutViewModel(private val cartRepository: CartRepository,
    private val menuRepository: MenuRepository,
    private val firebaseService: FirebaseService
) : ViewModel() {

    val checkoutData = cartRepository.getCheckoutData().asLiveData(Dispatchers.IO)

    fun checkoutCart() = menuRepository.createOrder(
        checkoutData.value?.payload?.first.orEmpty()
    ).asLiveData(Dispatchers.IO)

    fun getCurrentUser() = firebaseService.getCurrentUser()

    fun deleteAllCart() {
        viewModelScope.launch {
            cartRepository.deleteAllCart()
        }
    }
}